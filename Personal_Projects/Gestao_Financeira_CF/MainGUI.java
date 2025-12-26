import Controller.TransacaoService;
import Controller.ArmazenamentoDados;
import View.MainFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class MainGUI {
    public static void main(String[] args) {
        try {
            // Usar look and feel do sistema
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            TransacaoService service = new TransacaoService();
            ArmazenamentoDados.carregarDados(service);
            
            MainFrame frame = new MainFrame(service);
            frame.setVisible(true);
        });
    }
}
