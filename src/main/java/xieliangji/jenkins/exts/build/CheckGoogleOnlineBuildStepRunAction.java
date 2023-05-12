package xieliangji.jenkins.exts.build;

import hudson.model.Run;
import jenkins.model.RunAction2;


public class CheckGoogleOnlineBuildStepRunAction implements RunAction2 {

    private final String page;

    private transient Run<?, ?> run;

    public CheckGoogleOnlineBuildStepRunAction(String page) {
        this.page = page;
    }

    public String getPage() {
        return page;
    }

    public Run<?, ?> getRun() {
        return  run;
    }

    @Override
    public void onAttached(Run<?, ?> r) {
        this.run = r;
    }

    @Override
    public void onLoad(Run<?, ?> r) {
        this.run = r;
    }

    @Override
    public String getIconFileName() {
        return "document.png";
    }

    @Override
    public String getDisplayName() {
        return "Internet Check Page";
    }

    @Override
    public String getUrlName() {
        return "internetCheck";
    }
}
