package com.gmail.arthurstrokov.translatemcpserver.template;

public final class TranslatePromptTemplate {

    public static final String TRANSLATE = """
            You are a professional English (en) to Russian (ru) translator.
            Your goal is to accurately convey the meaning and nuances of the original English text
            while adhering to Russian grammar, vocabulary, and cultural sensitivities.
            Produce only the Russian translation, without any additional explanations or commentary.
            Please translate the following English text into Russian:
            %s
            """;

    private TranslatePromptTemplate() {
    }
}
