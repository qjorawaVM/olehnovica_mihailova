import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class PiercingAppointment {
    private JFrame frame;

    private JTextField nameField;
    private JSpinner ageSpinner;

    private JList piercingList;
    private JComboBox piercingChoice;

    private JButton submitButton;

    public static void main(String[] args) { SwingUtilities.invokeLater(() -> {

        PiercingAppointment appointment = new PiercingAppointment();
        appointment.createAndShowGUI();

    }); }

    private void createAndShowGUI() {



        frame = new JFrame("Pīrsinga pierakstīšana");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(600, 600);
        JPanel mainPanel = new JPanel(new GridLayout(8, 8, 8, 8));




        mainPanel.add(new JLabel("Ievadiet Jūsu vārdu un uzvārdu!"));
        nameField = new JTextField(20);

        mainPanel.add(nameField);

        SpinnerNumberModel ageModel = new SpinnerNumberModel(18, 18, 99, 1);
        ageSpinner = new JSpinner(ageModel);

        mainPanel.add(new JLabel("Cik Jums ir gadu?")); mainPanel.add(ageSpinner);

        ageSpinner.addChangeListener(e -> { int age = (int) ageSpinner.getValue();

            if (age < 18) { JOptionPane.showMessageDialog(frame, "Ja esat jaunāks par 18 gadiem, jūs nevarat pierakstīties.");

                System.exit(0); }

        });

        String[] piercingTypes = {"Helix", "Septum", "Medusa", "Daith", "Mēles"};
        piercingList = new JList(piercingTypes);

        piercingList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); piercingList.addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()) { String selectedType = (String) piercingList.getSelectedValue();

                JOptionPane.showMessageDialog(frame, getPiercingInfo(selectedType)); }

        }); mainPanel.add(new JLabel("Mūsu pīrsinga veidi:"));

        mainPanel.add(new JScrollPane(piercingList));

        piercingChoice = new JComboBox<>(piercingTypes);
        mainPanel.add(new JLabel("Kuru pīrsingu vēlaties?"));

        mainPanel.add(piercingChoice);

        submitButton = new JButton("Iesniegt"); submitButton.addActionListener(e -> {

            String name = nameField.getText(); int age = (int) ageSpinner.getValue();

            String piercingType = (String) piercingChoice.getSelectedItem();

            String info = "Vārds+Uzvārds: " + name + "\nVecums: " + age + "\nPīrsinga tips: " + piercingType;
            int response = JOptionPane.showConfirmDialog(frame, info, "Vai informācija ir pareiza?", JOptionPane.YES_NO_OPTION);

            if (response == JOptionPane.YES_OPTION) {

                JOptionPane.showMessageDialog(frame, "Paldies par pierakstu.💕"); } else {

                JOptionPane.showMessageDialog(frame, "Lūdzu aizpildiet blanku vēlreiz!"); System.exit(0);


            }
            try { FileWriter fw = new FileWriter("apointment.txt");
                fw.write("Vārds un Uzvārds: "+name);
                fw.write("\nVecums: "+age);
                fw.write("\nPīrsinga veids: "+piercingType);
                fw.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex); }});

        mainPanel.add(new JLabel()); mainPanel.add(submitButton);

        frame.getContentPane().add(mainPanel);
        JLabel headerLabel = new JLabel("Pīrsinga pierakstīšana❤");

        headerLabel.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 20));

        mainPanel.add(headerLabel);

        frame.setVisible(true);
    }


    private String getPiercingInfo(String type) {

        switch (type) { case "Helix":

            return "Spirāles pīrsings ir jebkurš pīrsings auss augšējā skrimšļa daļā.";
            case "Septum":

                return "Starpsienas caurduršana ir caurduršana deguna centrālajā daļā starp nāsīm.";
            case "Medusa":

                return "Medūzas jeb filtruma pīrsings ievieto tapu centrālajā rievā virs lūpas kupidona loka.";
            case "Daith":

                return "Daith pīrsings ir ķermeņa pīrsinga veids, kas ietver auss spirāles krustu.";

            case "Mēles":

                return "Mēles pīrsings ir ķermeņa pīrsings, ko parasti veic tieši caur mēles centru."; }

        return type;
    }
}
