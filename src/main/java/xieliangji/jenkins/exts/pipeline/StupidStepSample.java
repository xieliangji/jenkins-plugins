package xieliangji.jenkins.exts.pipeline;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.Extension;
import hudson.model.TaskListener;
import org.jenkinsci.plugins.workflow.steps.*;
import org.kohsuke.stapler.DataBoundConstructor;

import java.io.Serializable;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

/**
 * 一个流水线dsl方法，说一句蠢话，仅此而已
 */
public class StupidStepSample extends Step implements Serializable {

    private final String stupidMessage;

    @DataBoundConstructor
    public StupidStepSample(String stupidMessage) {
        this.stupidMessage = stupidMessage;
    }

    @Override
    public StepExecution start(StepContext context) throws Exception {
        return new SynchronousNonBlockingStepExecution<>(context) {
            @Override
            protected Object run() throws Exception {
                Objects.requireNonNull(getContext().get(TaskListener.class)).getLogger().println(stupidMessage);
                return null;
            }
        };
    }

    public String getStupidMessage() {
        return stupidMessage;
    }

    @Extension
    public static final class StupidStepSampleDescriptor extends StepDescriptor {

        @Override
        public Set<? extends Class<?>> getRequiredContext() {
            return Collections.singleton(TaskListener.class);
        }

        @Override
        public String getFunctionName() {
            return "stupidSay";
        }

        @NonNull
        @Override
        public String getDisplayName() {
            return "Say a stupid message";
        }
    }
}
