package xieliangji.jenkins.exts.official;

import org.jvnet.localizer.ResourceBundleHolder;

public class Messages {

    private final static ResourceBundleHolder holder = ResourceBundleHolder.get(Messages.class);

    public static String HelloWorldBuilderMissingMaster() {
        return holder.format("HelloWorldBuilder.MissingMaster");
    }

    public static String HelloWorldBuilderTooShort() {
        return holder.format("HelloWorldBuilder.TooShort");
    }

    public static String HelloWorldBuilderReallyChinese() {
        return holder.format("HelloWorldBuilder.ReallyChinese");
    }

    public static String HelloWorldBuilderDisplayName() {
        return holder.format("HelloWorldBuilder.DisplayName");
    }
}
