package xieliangji.jenkins.exts.official;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.EnvVars;
import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.AbstractProject;
import hudson.model.Run;
import hudson.model.TaskListener;
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

/**
 * 官网的HelloWorldBuilder
 */
public class HelloWorldBuilder extends Builder implements SimpleBuildStep {

    private final String master;
    private boolean useChinese;

    @DataBoundConstructor
    public HelloWorldBuilder(String master) {
        this.master = master;
    }

    public String getMaster() {
        return master;
    }

    public boolean isUseChinese() {
        return useChinese;
    }

    @DataBoundSetter
    public void setUseChinese(boolean useChinese) {
        this.useChinese = useChinese;
    }

    @Override
    public void perform(
            @NonNull Run<?, ?> run,
            @NonNull FilePath workspace,
            @NonNull EnvVars env,
            @NonNull Launcher launcher,
            @NonNull TaskListener listener) throws InterruptedException, IOException {
        run.addAction(new OfficialActionSample(master));
        if (useChinese) {
            listener.getLogger().printf("你好, %s", master);
        } else {
            listener.getLogger().printf("Hello, %s", master);
        }
    }

    @Symbol("问候")
    @Extension
    public static final class HelloWorldDescriptor extends BuildStepDescriptor<Builder> {

        public FormValidation doCheckMaster(@QueryParameter String value, @QueryParameter boolean useChinese) {
            if (StringUtils.isBlank(value)) {
                return FormValidation.error("主人不能为空");
            }
            if (StringUtils.length(value) < 4) {
                return FormValidation.warning("主人长度太短");
            }
            if (StringUtils.equals(value, "黎思静")) {
                return FormValidation.warning("白月光");
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
            return "主人";
        }
    }
}
