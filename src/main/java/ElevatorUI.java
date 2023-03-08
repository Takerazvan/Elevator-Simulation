    import com.codecool.elevator.Elevator;
    import com.codecool.elevator.Direction;

    import javax.swing.*;
    import javax.swing.border.Border;
    import java.awt.*;
    import java.awt.event.ActionEvent;
    import java.awt.event.ActionListener;

    public class ElevatorUI {
        private JFrame frame;
        private JPanel controlPanel;
        private JButton[] floorButtons;
        private JButton[] elevatorButtons;
        private JTextArea logTextArea;
        private JLabel directionLabel; // added label to show direction of elevator
        private Elevator elevator;

        private JPanel statusPanel;
        private JLabel currentFloorLabel;

        private JLabel requestsLabel;
        // Define button properties
        private final int BUTTON_WIDTH = 60;
        private final int BUTTON_HEIGHT = 60;
        private final int BUTTON_ARC_SIZE = 20;
        private final Color BUTTON_COLOR = Color.BLACK;
        private final Color BUTTON_HIGHLIGHT_COLOR = Color.BLACK;
        private final Font BUTTON_FONT = new Font("Rockwell", Font.BOLD, 28);
        private final Border BUTTON_BORDER = BorderFactory.createLineBorder(Color.GREEN, 4);
        private final Border BUTTON_ROUNDED_BORDER = BorderFactory.createLineBorder(Color.GREEN, 4, true);

        public ElevatorUI(int numFloors) {
            // Create elevator
            elevator = new Elevator(1, numFloors);

            // Create UI components
            frame = new JFrame("Elevator");
            controlPanel = new JPanel(new GridLayout(2, 1));
            floorButtons = new JButton[numFloors];
            elevatorButtons = new JButton[numFloors];
            logTextArea = new JTextArea();
            statusPanel = new JPanel(new GridLayout(3, 1));
            currentFloorLabel = new JLabel("", SwingConstants.CENTER);
            directionLabel = new JLabel("", SwingConstants.CENTER);
            requestsLabel = new JLabel("", SwingConstants.CENTER);
            statusPanel.add(currentFloorLabel);
            statusPanel.add(directionLabel);
            statusPanel.add(requestsLabel);
            controlPanel.add(statusPanel);

            // Add floor buttons to control panel
            JPanel floorButtonPanel = new JPanel(new GridLayout(1, numFloors));
            for (int i = numFloors - 1; i >= 0; i--) {
                floorButtons[i] = createButton(String.valueOf(i));
                floorButtons[i].addActionListener(new FloorButtonActionListener(i));
                floorButtonPanel.add(floorButtons[i]);
            }
            controlPanel.add(floorButtonPanel);

            // Add elevator buttons to control panel
            JPanel elevatorButtonPanel = new JPanel(new GridLayout(1, numFloors));
            for (int i = numFloors - 1; i >= 0; i--) {
                elevatorButtons[i] = createButton(String.valueOf(i));
                elevatorButtons[i].addActionListener(new ElevatorButtonActionListener(i));
                elevatorButtonPanel.add(elevatorButtons[i]);
            }
            controlPanel.add(elevatorButtonPanel);

            // Add direction label to control panel
            controlPanel.add(directionLabel);

            // Add log text area to control panel
            controlPanel.add(new JScrollPane(logTextArea));

            // Add control panel to frame
            frame.add(controlPanel);

            // Set frame properties
            frame.setSize(400, 600);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        }


        // Helper method to create a rounded button with metallic appearance
        private JButton createButton(String label) {
            JButton button = new JButton(label);
            button.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
            button.setBackground(BUTTON_COLOR);
            button.setFont(BUTTON_FONT);
            button.setBorder(BorderFactory.createCompoundBorder(BUTTON_BORDER, BUTTON_ROUNDED_BORDER));
            button.setOpaque(true);
            button.setContentAreaFilled(true);
            button.setFocusPainted(true);
            button.setForeground(Color.WHITE);
            button.setBackground(Color.BLACK);

            // Set button rollover effect
            button.getModel().addChangeListener(evt -> {
                ButtonModel model = (ButtonModel) evt.getSource();
                if (model.isRollover()) {
                    button.setBackground(BUTTON_HIGHLIGHT_COLOR);
                } else {
                    button.setBackground(BUTTON_COLOR);
                }
            });

            return button;
        }


        private class FloorButtonActionListener implements ActionListener {
            private final int floor;

            public FloorButtonActionListener(int floor) {
                this.floor = floor;
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                if (floor > elevator.getCurrentFloor()) {
                    elevator.addRequest(floor);
                    logTextArea.append("Added outside request: floor " + floor + ", direction UP.\n");
                } else if (floor < elevator.getCurrentFloor()) {
                    elevator.addRequest(floor);
                    logTextArea.append("Added outside request: floor " + floor + ", direction DOWN.\n");
                } else {
                    logTextArea.append("Elevator is already on floor " + floor + ".\n");
                }
            }
        }

        private class ElevatorButtonActionListener implements ActionListener {
            private final int floor;

            public ElevatorButtonActionListener(int floor) {
                this.floor = floor;
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                if (floor > elevator.getCurrentFloor()) {
                    elevator.addRequest(floor);
                    logTextArea.append("Added inside request: floor " + floor + ", direction UP.\n");
                } else if (floor < elevator.getCurrentFloor()) {
                    elevator.addRequest(floor);
                    logTextArea.append("Added inside request: floor " + floor + ", direction DOWN.\n");
                } else {
                    logTextArea.append("Elevator is already on floor " + floor + ".\n");
                }
            }
        }
        public static void main(String[] args) throws InterruptedException {
            ElevatorUI elevatorUI = new ElevatorUI(5);

    // Load the image from the file
            ImageIcon icon = new ImageIcon("C:\\Users\\Asus\\Desktop\\GREENARROW.png");
            ImageIcon imageIcon= new ImageIcon("C:\\Users\\Asus\\Desktop\\GREEN2.png");


            // Start elevator
            while (true) {
                elevatorUI.elevator.move();
                Thread.sleep(4000); // Wait for 3 seconds before moving again

                if (elevatorUI.elevator.getDirection() == Direction.UP) {
                    // Set the icon on the label
                    elevatorUI.directionLabel.setIcon(icon);
                } else if(elevatorUI.elevator.getDirection()==Direction.DOWN)  {
                    elevatorUI.directionLabel.setIcon(imageIcon);

                } else {

                    elevatorUI.directionLabel.setIcon(null);

                }

                // Update status panel
                elevatorUI.currentFloorLabel.setText("Current floor: " + elevatorUI.elevator.getCurrentFloor());
                elevatorUI.directionLabel.setText("Direction: " + elevatorUI.elevator.getDirection());
                elevatorUI.requestsLabel.setText("Requests: " + elevatorUI.elevator.getRequests());
            }
            }
        }
