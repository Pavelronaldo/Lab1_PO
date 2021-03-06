import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicInteger;

public class SemaphoreView extends MainClass {
    private static final AtomicInteger semaphore = new AtomicInteger(0);
    JButton buttonStart2;
    JButton buttonStop2;

    @Override
    void initButtons() {
        buttonStart = new JButton("Start1");
        buttonStop = new JButton("Stop1");

        buttonStart2 = new JButton("Start2");
        buttonStop2 = new JButton("Stop2");

        initButtonsEnabled();
    }

    @Override
    void initListener() {
        listener = e -> {
            if (e.getSource() == buttonStart) {
                if (semaphore.compareAndSet(0, 1)) {
                    thread1 = new MyThread(10, this);
                    thread1.setMinPriority();

                    buttonStart.setEnabled(false);
                    buttonStop.setEnabled(true);

                    buttonStart2.setEnabled(true);
                    buttonStop2.setEnabled(false);
                } else {
                    System.out.println("Занято другим потоком");
                }

            } else if (e.getSource() == buttonStop) {

                semaphore.set(0);
                thread1.setRunning(false);

                initButtonsEnabled();

            } else if (e.getSource() == buttonStart2) {
                if (semaphore.compareAndSet(0, 1)) {
                    thread2 = new MyThread(90, this);
                    thread2.setMaxPriority();

                    buttonStart.setEnabled(true);
                    buttonStop.setEnabled(false);

                    buttonStart2.setEnabled(false);
                    buttonStop2.setEnabled(true);
                } else {
                    System.out.println("Занято другим потоком...");

                }
            } else if (e.getSource() == buttonStop2) {
                semaphore.set(0);
                thread2.setRunning(false);
                initButtonsEnabled();

            } else {
                System.out.println("Неизвестная кнопка");
            }
        };

        buttonStart.addActionListener(listener);
        buttonStop.addActionListener(listener);
        buttonStart2.addActionListener(listener);
        buttonStop2.addActionListener(listener);
    }

    void initButtonsEnabled() {
        buttonStart.setEnabled(true);
        buttonStop.setEnabled(false);

        buttonStart2.setEnabled(true);
        buttonStop2.setEnabled(false);
    }

    @Override
    void initLayouts() {
        layoutTop = new GridLayout(1, 1);
        layoutLeftRight = new GridLayout(3, 1);
        layoutBottom = new GridLayout(1, 2);
    }

    void initContent() {
        contentTop = new JPanel();

        contentTop.setLayout(layoutTop);

        contentLeft = new JPanel();
        contentRight = new JPanel();
        contentBottom = new JPanel();

        contentMain = new JPanel();

        contentLeft.setLayout(layoutLeftRight);
        contentRight.setLayout(layoutLeftRight);
        contentBottom.setLayout(layoutBottom);

        contentMain.setLayout(layoutLeftRight);

        contentLeft.add(buttonStart);
        contentLeft.add(buttonStop);

        contentRight.add(buttonStart2);
        contentRight.add(buttonStop2);

        contentTop.add(slider);

        contentBottom.add(contentLeft);
        contentBottom.add(contentRight);

        contentMain.add(contentTop);
        contentMain.add(contentBottom);
    }


    public SemaphoreView() {
    }

    public static void main(String[] args) {
        SemaphoreView semaphoreView = new SemaphoreView();
    }
}
