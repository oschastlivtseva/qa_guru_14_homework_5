package guru.qa;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.CollectionCondition.itemWithText;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withTagAndText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.url;

public class FirstTest {
    @BeforeAll
    static void configure() {
        Configuration.baseUrl = "https://github.com";
    }

    @Test
    void firstTest() {
        String targetPage = "SoftAssertions";
        String targetPageSection = "JUnit5";

        // Откройте страницу Selenide в Github
        open("/selenide/selenide");

        // Перейдите в раздел Wiki проекта
        $("#wiki-tab").click();
        $("#wiki-wrapper").shouldBe(visible);
        assert url().contains("/selenide/selenide/wiki");

        // Убедитесь, что в списке страниц (Pages) есть страница SoftAssertions
        ElementsCollection wikiPages = $("#wiki-pages-box").$$(".Box-row");
        if (!wikiPages.toString().contains(targetPage)) {
            $(withText("more pages…")).click();
        }
        wikiPages.shouldHave(itemWithText(targetPage));

        // Откройте страницу SoftAssertions, проверьте что внутри есть пример кода для JUnit5
        wikiPages.findBy(text(targetPage)).click();
        $("h1").shouldHave(text(targetPage));
        $(withTagAndText("h4", targetPageSection)).sibling(0).shouldHave(cssClass("highlight-source-java"));
    }
}
