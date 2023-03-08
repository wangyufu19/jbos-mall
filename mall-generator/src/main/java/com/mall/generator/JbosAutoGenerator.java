package com.mall.generator;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine;
import org.jetbrains.annotations.NotNull;

import java.util.Scanner;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public final class JbosAutoGenerator {
    private final DataSourceConfig.Builder dataSourceConfigBuilder;
    private final com.baomidou.mybatisplus.generator.config.GlobalConfig.Builder globalConfigBuilder;
    private final com.baomidou.mybatisplus.generator.config.PackageConfig.Builder packageConfigBuilder;
    private final com.baomidou.mybatisplus.generator.config.StrategyConfig.Builder strategyConfigBuilder;
    private final com.baomidou.mybatisplus.generator.config.InjectionConfig.Builder injectionConfigBuilder;
    private final com.baomidou.mybatisplus.generator.config.TemplateConfig.Builder templateConfigBuilder;
    private AbstractTemplateEngine templateEngine;
    private final Scanner scanner;

    private JbosAutoGenerator(DataSourceConfig.Builder dataSourceConfigBuilder) {
        this.scanner = new Scanner(System.in);
        this.dataSourceConfigBuilder = dataSourceConfigBuilder;
        this.globalConfigBuilder = new com.baomidou.mybatisplus.generator.config.GlobalConfig.Builder();
        this.packageConfigBuilder = new com.baomidou.mybatisplus.generator.config.PackageConfig.Builder();
        this.strategyConfigBuilder = new com.baomidou.mybatisplus.generator.config.StrategyConfig.Builder();
        this.injectionConfigBuilder = new com.baomidou.mybatisplus.generator.config.InjectionConfig.Builder();
        this.templateConfigBuilder = new com.baomidou.mybatisplus.generator.config.TemplateConfig.Builder();
    }

    public static JbosAutoGenerator create(@NotNull String url, String username, String password) {
        return new JbosAutoGenerator(new DataSourceConfig.Builder(url, username, password));
    }

    public static JbosAutoGenerator create(@NotNull DataSourceConfig.Builder dataSourceConfigBuilder) {
        return new JbosAutoGenerator(dataSourceConfigBuilder);
    }

    public String scannerNext(String message) {
        System.out.println(message);
        String nextLine = this.scanner.nextLine();
        return StringUtils.isBlank(nextLine) ? this.scanner.next() : nextLine;
    }

    public JbosAutoGenerator globalConfig(Consumer<GlobalConfig.Builder> consumer) {
        consumer.accept(this.globalConfigBuilder);
        return this;
    }

    public JbosAutoGenerator globalConfig(BiConsumer<Function<String, String>, GlobalConfig.Builder> biConsumer) {
        biConsumer.accept((message) -> {
            return this.scannerNext(message);
        }, this.globalConfigBuilder);
        return this;
    }

    public JbosAutoGenerator packageConfig(Consumer<com.baomidou.mybatisplus.generator.config.PackageConfig.Builder> consumer) {
        consumer.accept(this.packageConfigBuilder);
        return this;
    }

    public JbosAutoGenerator packageConfig(BiConsumer<Function<String, String>, com.baomidou.mybatisplus.generator.config.PackageConfig.Builder> biConsumer) {
        biConsumer.accept((message) -> {
            return this.scannerNext(message);
        }, this.packageConfigBuilder);
        return this;
    }

    public JbosAutoGenerator strategyConfig(Consumer<com.baomidou.mybatisplus.generator.config.StrategyConfig.Builder> consumer) {
        consumer.accept(this.strategyConfigBuilder);
        return this;
    }

    public JbosAutoGenerator strategyConfig(BiConsumer<Function<String, String>, com.baomidou.mybatisplus.generator.config.StrategyConfig.Builder> biConsumer) {
        biConsumer.accept((message) -> {
            return this.scannerNext(message);
        }, this.strategyConfigBuilder);
        return this;
    }

    public JbosAutoGenerator injectionConfig(Consumer<com.baomidou.mybatisplus.generator.config.InjectionConfig.Builder> consumer) {
        consumer.accept(this.injectionConfigBuilder);
        return this;
    }

    public JbosAutoGenerator injectionConfig(BiConsumer<Function<String, String>, com.baomidou.mybatisplus.generator.config.InjectionConfig.Builder> biConsumer) {
        biConsumer.accept((message) -> {
            return this.scannerNext(message);
        }, this.injectionConfigBuilder);
        return this;
    }

    public JbosAutoGenerator templateConfig(Consumer<com.baomidou.mybatisplus.generator.config.TemplateConfig.Builder> consumer) {
        consumer.accept(this.templateConfigBuilder);
        return this;
    }

    public JbosAutoGenerator templateConfig(BiConsumer<Function<String, String>, com.baomidou.mybatisplus.generator.config.TemplateConfig.Builder> biConsumer) {
        biConsumer.accept((message) -> {
            return this.scannerNext(message);
        }, this.templateConfigBuilder);
        return this;
    }

    public JbosAutoGenerator templateEngine(AbstractTemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
        return this;
    }

    public void execute() {
        (new AutoGenerator(this.dataSourceConfigBuilder.build())).global(this.globalConfigBuilder.build()).packageInfo(this.packageConfigBuilder.build()).strategy(this.strategyConfigBuilder.build()).injection(this.injectionConfigBuilder.build()).template(this.templateConfigBuilder.build()).execute(this.templateEngine);
    }
}
