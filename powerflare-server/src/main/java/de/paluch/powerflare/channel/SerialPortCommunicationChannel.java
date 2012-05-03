package de.paluch.powerflare.channel;

import gnu.io.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created with IntelliJ IDEA. User: mark Date: 27.04.12 Time: 08:28 To change this template use File | Settings | File
 * Templates.
 */
public class SerialPortCommunicationChannel extends AbstractCommunicationChannel implements ICommunicationChannel {
    private final SerialPort port;
    private InputStream in;
    private OutputStream out;

    public SerialPortCommunicationChannel(String device, int baudRate)
            throws NoSuchPortException, PortInUseException, UnsupportedCommOperationException, IOException {
        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(device);
        port = (SerialPort) portIdentifier.open(getClass().getName(), 2000);
        port.setSerialPortParams(baudRate, SerialPort.DATABITS_8, SerialPort.STOPBITS_2, SerialPort.PARITY_NONE);

        in = port.getInputStream();
        out = port.getOutputStream();

    }


    public void sendData(byte[] data) {

        synchronized (port) {
            try {

                out.write(data);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    public void close() {
        if (in != null) {
            try {
                in.close();
            } catch (IOException e) {
                // nothing to do.
            }
        }

        if (out != null) {
            try {
                out.close();
            } catch (IOException e) {
                // nothing to do.
            }
        }

        port.close();
    }
}
