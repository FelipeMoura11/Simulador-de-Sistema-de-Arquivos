package SimuladorSistemaArquivos;

public class FileEntry {
    private String name;

    public FileEntry(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void rename(String newName) {
        this.name = newName;
    }

    @Override
    public String toString() {
        return "Arquivo: " + name;
    }
}