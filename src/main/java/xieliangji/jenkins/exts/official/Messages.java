package xieliangji.jenkins.exts.official;

import org.jvnet.localizer.Localizable;
import org.jvnet.localizer.ResourceBundleHolder;

/**
 * Java代码中的国际化{@link ResourceBundleHolder}
 * Jenkins使用{@link org.jvnet.localizer.Localizable} <a href="https://github.com/kohsuke/localizer">...</a> 和Maven插件
 *
 */
public class Messages {

    private static final String HelloWorldBuilderMissingMaster = "HelloWorldBuilder.MissingMaster";
    private static final String HelloWorldBuilderTooShort = "HelloWorldBuilder.TooShort";
    private static final String HelloWorldBuilderReallyChinese = "HelloWorldBuilder.ReallyChinese";
    private static final String HelloWorldBuilderDisplayName = "HelloWorldBuilder.DisplayName";

    private final static ResourceBundleHolder holder = ResourceBundleHolder.get(Messages.class);

    public static String HelloWorldBuilderMissingMaster() {
        return holder.format(HelloWorldBuilderMissingMaster);
    }

    public static Localizable _HelloWorldBuilderMissingMaster() {
        return new Localizable(holder, HelloWorldBuilderMissingMaster);
    }

    public static String HelloWorldBuilderTooShort() {
        return holder.format(HelloWorldBuilderTooShort);
    }

    public static Localizable _HelloWorldBuilderTooShort() {
        return new Localizable(holder, HelloWorldBuilderTooShort);
    }

    public static String HelloWorldBuilderReallyChinese() {
        return holder.format(HelloWorldBuilderReallyChinese);
    }

    public static Localizable _HelloWorldBuilderReallyChinese() {
        return new Localizable(holder, HelloWorldBuilderReallyChinese);
    }

    public static String HelloWorldBuilderDisplayName() {
        return holder.format(HelloWorldBuilderDisplayName);
    }

    public static Localizable _HelloWorldBuilderDisplayName() {
        return new Localizable(holder, HelloWorldBuilderDisplayName);
    }
}
