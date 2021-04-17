package com.sprintpay.payments.msreport;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.sprintpay.payments.msreport");

        noClasses()
            .that()
                .resideInAnyPackage("com.sprintpay.payments.msreport.service..")
            .or()
                .resideInAnyPackage("com.sprintpay.payments.msreport.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..com.sprintpay.payments.msreport.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
