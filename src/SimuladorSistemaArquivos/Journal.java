package SimuladorSistemaArquivos;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class Journal {
    private final String caminhoLog;

    public Journal(String caminhoLog) {
        this.caminhoLog = caminhoLog;
    }

    public void registrar(String operacao) {
        String entrada = "[" + LocalDateTime.now() + "] " + operacao;

        try (FileWriter writer = new FileWriter(caminhoLog, true)) {
            writer.write(entrada + "\n");
        } catch (IOException e) {
            System.out.println("❌ Erro ao registrar no journal: " + e.getMessage());
        }

        System.out.println("📝 [JOURNAL] " + entrada);
    }
}