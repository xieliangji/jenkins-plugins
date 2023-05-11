package xieliangji.jenkins.exts.official;

import hudson.model.Run;
import jenkins.model.RunAction2;

/**
 * Jenkins官网教程action
 */
public class OfficialActionSample implements RunAction2 {

    private final String modelName;

    private transient Run<?, ?> run;

    public OfficialActionSample(String modelName) {
        this.modelName = modelName;
    }

    public String getModelName() {
        return modelName;
    }

    public Run<?, ?> getRun() {
        return run;
    }

    @Override
    public String getIconFileName() {
        return "document.png";
    }

    @Override
    public String getDisplayName() {
        return "Action Sample";
    }

    @Override
    public String getUrlName() {
        return "actionSample";
    }

    @Override
    public void onAttached(Run<?, ?> r) {
        this.run = r;
    }

    @Override
    public void onLoad(Run<?, ?> r) {
        this.run = r;
    }
}
