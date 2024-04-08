import java.util.List;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Visualization extends JFrame {
    private JPanel contentPane;
    private JPanel panel;
    private JSlider sliderNumElements;
    private JSlider sliderSpeed;
    private JLabel minValueLbl1;
    private JLabel maxValueLbl1;
    private JLabel NumElementsLbl;
    private JLabel minValueLbl2;
    private JLabel maxValueLbl2;
    private JLabel SpeedLbl;
    private JComboBox<String> comboBox;
    private JButton sortButton;
    private JButton generateElementsButton;
    private List<Integer> numbers;
    private int sleepValue = 1;

    public Visualization() {
        this.setTitle("Sort Visualiser");
        this.setResizable(false);
        this.setDefaultCloseOperation(1);
        this.setBounds(200, 200, 600, 500);
        this.contentPane = new JPanel();
        this.setContentPane(this.contentPane);
        this.contentPane.setLayout((LayoutManager) null);
        this.panel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int numElements = Visualization.this.numbers.size();
                int width = this.getWidth();
                int columnWidth = width / numElements;
                int height = this.getHeight() / (Integer) Collections.max(Visualization.this.numbers);
                int remainingSpace = width - numElements * columnWidth;
                int startX = remainingSpace;

                for (int i = 0; i < numElements; i++) {
                    int columnHeight = (Integer) Visualization.this.numbers.get(i) * height;
                    g.setColor(Color.RED);
                    int x = startX + i * columnWidth;
                    int y = this.getHeight() - columnHeight;
                    g.fillRect(x, y, columnWidth, columnHeight);
                }
            }
        };

        this.panel.setBounds(20, 140, 480, 280);
        this.contentPane.add(this.panel);


        this.sliderNumElements = new JSlider();
        this.sliderNumElements.setBounds(60, 50, 150, 20);
        this.sliderNumElements.setMinimum(5);
        this.sliderNumElements.setMaximum(50);
        this.contentPane.add(this.sliderNumElements);

        this.minValueLbl1 = new JLabel("5");
        this.minValueLbl1.setHorizontalAlignment(0);
        this.minValueLbl1.setBounds(35, 50, 25, 15);
        this.contentPane.add(this.minValueLbl1);
        this.maxValueLbl1 = new JLabel("50");
        this.maxValueLbl1.setHorizontalAlignment(0);
        this.maxValueLbl1.setBounds(210, 50, 25, 15);
        this.contentPane.add(this.maxValueLbl1);

        this.NumElementsLbl = new JLabel("Number of elements");
        this.NumElementsLbl.setHorizontalAlignment(0);
        this.NumElementsLbl.setBounds(60, 70, 140, 15);
        this.contentPane.add(this.NumElementsLbl);

        this.sliderSpeed = new JSlider();
        this.sliderSpeed.setMinimum(1);
        this.sliderSpeed.setMaximum(50);
        this.sliderSpeed.setValue(50);
        this.sliderSpeed.setBounds(60, 100, 150, 20);
        this.contentPane.add(this.sliderSpeed);

        this.minValueLbl2 = new JLabel("x1");
        this.minValueLbl2.setHorizontalAlignment(0);
        this.minValueLbl2.setBounds(35, 100, 25, 15);
        this.contentPane.add(this.minValueLbl2);
        this.maxValueLbl2 = new JLabel("x4");
        this.maxValueLbl2.setHorizontalAlignment(0);
        this.maxValueLbl2.setBounds(210, 100, 25, 15);
        this.contentPane.add(this.maxValueLbl2);

        this.SpeedLbl = new JLabel("Speed");
        this.SpeedLbl.setHorizontalAlignment(0);
        this.SpeedLbl.setBounds(90, 120, 80, 15);
        this.contentPane.add(this.SpeedLbl);

        this.comboBox = new JComboBox();
        this.comboBox.setBounds(260, 50, 120, 30);
        this.comboBox.setModel(new DefaultComboBoxModel(new String[]{"Bubble Sort", "Selection Sort", "Insertion Sort", "Quick Sort", "Merge Sort", "Heap Sort"}));
        this.contentPane.add(this.comboBox);

        this.sortButton = new JButton("Sort");
        this.sortButton.setBounds(400, 50, 100, 30);
        this.contentPane.add(this.sortButton);

        this.generateElementsButton = new JButton("Generate random elements");
        this.generateElementsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Visualization.this.generateRandomNumbers(Visualization.this.sliderNumElements.getValue());
                Visualization.this.panel.repaint();
            }
        });
        this.generateElementsButton.setBounds(280, 100, 200, 30);
        this.contentPane.add(this.generateElementsButton);

        this.sliderSpeed.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                int speedValue = Visualization.this.sliderSpeed.getValue();
                Visualization.this.sleepValue = 80 - speedValue + 1;
            }
        });
        this.sortButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == sortButton) {
                    String selectedSort = (String) comboBox.getSelectedItem();
                    switch (selectedSort) {
                        case "Bubble Sort":
                            (new Thread(() -> {
                                Visualization.this.bubbleSort(Visualization.this.numbers);
                            })).start();
                            break;
                        case "Selection Sort":
                            (new Thread(() -> {
                                Visualization.this.selectionSort(Visualization.this.numbers);
                            })).start();
                            break;
                        case "Insertion Sort":
                            (new Thread(() -> {
                                Visualization.this.insertionSort(Visualization.this.numbers);
                            })).start();
                            break;
                        case "Quick Sort":
                            (new Thread(() -> {
                                Visualization.this.quickSort(Visualization.this.numbers, 0, Visualization.this.numbers.size() - 1);
                            })).start();
                            break;
                        case "Merge Sort":
                            (new Thread(() -> {
                                Visualization.this.mergeSort(Visualization.this.numbers, 0, Visualization.this.numbers.size() - 1);
                            })).start();
                            break;
                        case "Heap Sort":
                            (new Thread(() -> {
                                Visualization.this.heapSort(Visualization.this.numbers);
                            })).start();
                    }
                }
            }
        });
        this.generateRandomNumbers(this.sliderNumElements.getValue());
    }

    private void generateRandomNumbers(int size) {
        this.numbers = new ArrayList();

        for (int i = 0; i < size; i++) {
            this.numbers.add((int) (Math.random() * 100.0) + 1);
        }

    }





    private void bubbleSort(List<Integer> list) {
        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if ((Integer) list.get(j) > (Integer) list.get(j + 1)) {
                    int temp = (Integer) list.get(j);
                    list.set(j, (Integer) list.get(j + 1));
                    list.set(j + 1, temp);
                    this.panel.repaint();
                    try {
                        Thread.sleep((long)this.sleepValue);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    private void selectionSort(List<Integer> list) {
        int n = list.size();

        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;

            for (int j = i + 1; j < n; j++) {
                if ((Integer) list.get(j) < (Integer) list.get(minIndex)) {
                    minIndex = j;
                }
            }

            if (minIndex != i) {
                Collections.swap(list, i, minIndex);
                this.panel.repaint();
                try {
                    Thread.sleep((long)this.sleepValue);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void insertionSort(List<Integer> list) {
        int n = list.size();
        for (int i = 1; i < n; i++) {
            int key = (Integer) list.get(i);

            int j;
            for (j = i - 1; j >= 0 && (Integer) list.get(j) > key; --j) {
                list.set(j + 1, (Integer) list.get(j));
            }

            list.set(j + 1, key);
            this.panel.repaint();
            try {
                Thread.sleep((long)this.sleepValue);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    private void quickSort(List<Integer> list, int first, int last) {
        if (first < last) {
            int pivot = this.partition(list, first, last);
            this.quickSort(list, first, pivot - 1);
            this.quickSort(list, pivot + 1, last);
        }
    }

    private int partition(List<Integer> list, int first, int last) {
        int pivot = (Integer) list.get(last);
        int i = first - 1;
        for (int j = first; j < last; ++j) {
            if ((Integer) list.get(j) < pivot) {
                ++i;
                Collections.swap(list, i, j);
                this.panel.repaint();
                try {
                    Thread.sleep((long)this.sleepValue);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        Collections.swap(list, i + 1, last);
        return i + 1;
    }
    private void mergeSort(List<Integer> list, int left, int right) {
        if (left < right) {
            int m = (left + right) / 2;
            this.mergeSort(list, left, m);
            this.mergeSort(list, m + 1, right);
            this.merge(list, left, m, right);
        }

    }
    private void merge(List<Integer> list, int left, int middle, int right) {
        int n1 = middle - left + 1;
        int n2 = right - middle;
        List<Integer> L = new ArrayList(list.subList(left, middle + 1));
        List<Integer> R = new ArrayList(list.subList(middle + 1, right + 1));
        int i = 0;
        int j = 0;
        int k = left;

        while (i < n1 && j < n2) {
            if ((Integer) L.get(i) <= (Integer) R.get(j)) {
                list.set(k, (Integer) L.get(i));
                i++;
            } else {
                list.set(k, (Integer) R.get(j));
                j++;
            }
            k++;
            this.panel.repaint();
            try {
                Thread.sleep((long)this.sleepValue);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        while (i < n1) {
            list.set(k, (Integer) L.get(i));
            i++;
            k++;
        }

        while (j < n2) {
            list.set(k, (Integer) R.get(j));
            j++;
            k++;
        }

    }

    private void heapSort(List<Integer> list) {
        int n = list.size();

        int i;
        for (i = n / 2 - 1; i >= 0; --i) {
            this.heap(list, n, i);
        }

        for (i = n - 1; i >= 0; --i) {
            Collections.swap(list, 0, i);
            this.heap(list, i, 0);
            this.panel.repaint();
            try {
                Thread.sleep((long)this.sleepValue);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void heap(List<Integer> list, int n, int i) {
        int largest = i;
        int l = 2 * i + 1;
        int r = 2 * i + 2;
        if (l < n && (Integer) list.get(l) > (Integer) list.get(i)) {
            largest = l;
        }

        if (r < n && (Integer) list.get(r) > (Integer) list.get(largest)) {
            largest = r;
        }

        if (largest != i) {
            Collections.swap(list, i, largest);
            this.heap(list, n, largest);
        }

    }
}

