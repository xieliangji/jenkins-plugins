package xieliangji.jenkins.exts.build;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.Extension;
import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.BuildListener;
import hudson.tasks.BuildWrapper;
import hudson.tasks.BuildWrapperDescriptor;
import org.kohsuke.stapler.DataBoundConstructor;

import java.io.IOException;

/**
 * Jenkins构建环境扩展小样
 * 在构建的结束时简单地打印一条日志及打印构建的编号
 * {@link hudson.ExtensionPoint} {@link hudson.tasks.BuildWrapper}
 */
public class EnvironmentSetupSample extends BuildWrapper {

    @DataBoundConstructor
    public EnvironmentSetupSample() {

    }

    @Override
    public Environment setUp(AbstractBuild build, final Launcher launcher, BuildListener listener) {
        return new Environment() {
            @Override
            public boolean tearDown(AbstractBuild build, BuildListener listener) throws IOException, InterruptedException {
                listener.getLogger().printf("%s 来追我，如果你追到我，我就让你嘿嘿嘿...%n", build.getNumber());
                return super.tearDown(build, listener);
            }
        };
    }


    @Extension
    public static final class EnvironmentSetupSampleDescriptor extends BuildWrapperDescriptor {

        @Override
        public boolean isApplicable(AbstractProject<?, ?> item) {
            // applicable for any job
            return true;
        }

        @NonNull
        @Override
        public String getDisplayName() {
            return "我就是太阳";
        }
    }
}
