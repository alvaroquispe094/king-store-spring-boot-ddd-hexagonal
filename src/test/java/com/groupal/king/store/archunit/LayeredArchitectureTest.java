package com.groupal.king.store.archunit;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchIgnore;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.Architectures;


@AnalyzeClasses(packages = "com.groupal.king.store", importOptions = ImportOption.DoNotIncludeTests.class)
public class LayeredArchitectureTest {

    private static final String DOMAIN = "Domain";
    private static final String ADAPTERS = "Adapters";
    private static final String APPLICATION = "Application";
    private static final String CONFIG = "Config";

    @ArchTest
    @ArchIgnore
    public static final ArchRule layer_dependencies_are_respected = Architectures.layeredArchitecture()
            .layer(CONFIG).definedBy("com.groupal.king.store.config")
            .layer(DOMAIN).definedBy("com.groupal.king.store.domain")
            .layer(ADAPTERS).definedBy("com.groupal.king.store.adapter")
            .layer(APPLICATION).definedBy("com.groupal.king.store.application")

            .whereLayer(APPLICATION).mayOnlyBeAccessedByLayers(ADAPTERS, CONFIG)
            .whereLayer(ADAPTERS).mayOnlyBeAccessedByLayers(CONFIG)
            .whereLayer(DOMAIN).mayOnlyBeAccessedByLayers(APPLICATION, ADAPTERS, CONFIG);

}
