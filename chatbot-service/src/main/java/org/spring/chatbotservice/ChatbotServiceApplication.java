package org.spring.chatbotservice;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

import java.util.List;

@SpringBootApplication
public class ChatbotServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatbotServiceApplication.class, args);
    }
    @Bean
    CommandLineRunner commandLineRunner(VectorStore vectorStore) {
        return args -> {
            try {
                PagePdfDocumentReader pdfDocumentReader = new PagePdfDocumentReader(
                        new ClassPathResource("13.pdf")
                );
                List<Document> documents = pdfDocumentReader.get();

                TextSplitter textSplitter = new TokenTextSplitter();
                List<Document> chunks = textSplitter.split(documents);

                vectorStore.add(chunks);

            } catch (Exception e) {
                throw new RuntimeException("Traitement erroné du fichier PDF : " + e.getMessage(), e);
            }
        };
    }

}