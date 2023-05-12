package xieliangji.jenkins.exts.build;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.EnvVars;
import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.*;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Builder;
import hudson.util.FormValidation;
import jenkins.tasks.SimpleBuildStep;
import org.apache.commons.lang.StringUtils;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;
import org.kohsuke.stapler.QueryParameter;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Jenkins Build Step: 检查是否可以访问国际网络
 */
public class CheckGoogleOnlineBuildStep extends Builder implements SimpleBuildStep {

    private String googleUrl;

    private boolean withNCR;

    @DataBoundConstructor
    public CheckGoogleOnlineBuildStep(String googleUrl) {
        this.googleUrl = googleUrl;
    }

    public String getGoogleUrl() {
        return googleUrl;
    }

    public boolean isWithNCR() {
        return withNCR;
    }

    @DataBoundSetter
    public void setWithNCR(boolean withNCR) {
        this.withNCR = withNCR;
    }

    @Override
    public void perform(
            @NonNull Run<?, ?> run,
            @NonNull FilePath workspace,
            @NonNull EnvVars env,
            @NonNull Launcher launcher,
            @NonNull TaskListener listener) throws InterruptedException, IOException {
        if (withNCR) {
            googleUrl = googleUrl.endsWith("/") ? googleUrl.substring(0, googleUrl.length() - 1) : googleUrl;
            googleUrl = googleUrl.concat("/ncr");
        }
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder().GET().uri(URI.create(googleUrl)).build();
        HttpResponse<String> httpResponse =
                httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
        if (httpResponse.statusCode() == 200) {
            run.addAction(new CheckGoogleOnlineBuildStepRunAction(httpResponse.body()));
            listener.getLogger().println("check internet access successfully, build go on...");
        } else {
            run.addAction(new CheckGoogleOnlineBuildStepRunAction("Sorry, the internet cannot access"));
            listener.getLogger().println("check internet access failed. build stop right now.");
            Executor executor = run.getExecutor();
            if (Objects.nonNull(executor)) {
                org.kohsuke.stapler.HttpResponse resp = executor.doStop();
                listener.getLogger().printf("stop executor: %s", resp.toString());
            }
        }
    }

    @Symbol("测试国际网络能否访问")
    @Extension
    public static final class CheckGoogleOnlineBuildStepDescriptor extends BuildStepDescriptor<Builder> {

        public FormValidation checkForm(@QueryParameter String googleUrl, @QueryParameter boolean withNCR) {
            Pattern urlPattern = Pattern.compile("^https?://([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?");
            if (!urlPattern.matcher(googleUrl).matches()) {
                return FormValidation.error("不合规范的url");
            }
            try {
                URI uri = URI.create(googleUrl);
                if (StringUtils.isNotBlank(uri.getQuery()) || googleUrl.contains("?")) {
                    return FormValidation.error("包含查询参数");
                }
            } catch (Exception ex) {
                return FormValidation.error(ex.getMessage());
            }
            return FormValidation.ok();
        }

        @Override
        public boolean isApplicable(Class<? extends AbstractProject> jobType) {
            return true;
        }

        @NonNull
        @Override
        public String getDisplayName() {
            return "国际网络访问测试";
        }
    }
}
