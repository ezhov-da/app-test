package ru.ezhov.test.audio;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.vosk.LibVosk;
import org.vosk.LogLevel;
import org.vosk.Model;
import org.vosk.Recognizer;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AudioApp {
    public static void main(String[] args) {

        LibVosk.setLogLevel(LogLevel.DEBUG);

        AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 60000, 16, 2, 4, 44100, false);
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
        TargetDataLine microphone;
        SourceDataLine speakers;

        try (Model model = new Model("D:\\vosk-model-small-ru-0.22");
             Recognizer recognizer = new Recognizer(model, 120000)) {

            microphone = (TargetDataLine) AudioSystem.getLine(info);
            microphone.open(format);
            microphone.start();

//            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int numBytesRead;
            int CHUNK_SIZE = 1024;
            int bytesRead = 0;

            DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, format);
//            speakers = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
//            speakers.open(format);
//            speakers.start();
            byte[] b = new byte[4096];

            String friendName = "джарвис";

            while (true) {
//            while (bytesRead <= 100000000) {
                numBytesRead = microphone.read(b, 0, CHUNK_SIZE);
                bytesRead += numBytesRead;

//                out.write(b, 0, numBytesRead);

//                speakers.write(b, 0, numBytesRead);

                String lastValue = "";

                if (recognizer.acceptWaveForm(b, numBytesRead)) {
                    String result = recognizer.getResult();
                    if (!"".equals(result)) {
                        ObjectMapper mapper = new ObjectMapper();
                        lastValue = mapper.readTree(result).get("text").asText();
                        ;
                    }
                } else {
//                    System.out.println(recognizer.getPartialResult());
                }

                if (!"".equals(lastValue)) {
                    System.out.println(lastValue);
                }

                if (lastValue.startsWith(friendName)) {
                    if (lastValue.startsWith(friendName + " задача")) {
                        List<String> list = Arrays.asList(lastValue.split(" "));
                        if (list.size() > 2) {
                            List<String> words = list.subList(2, list.size());

                            Map<String, String> numbers = new HashMap<String, String>() {
                                {
                                    put("один", "1");
                                    put("два", "2");
                                    put("три", "3");
                                    put("четыре", "4");
                                    put("пять", "5");
                                    put("шесть", "6");
                                    put("семь", "7");
                                    put("восемь", "8");
                                    put("девять", "9");
                                    put("ноль", "0");
                                }
                            };

                            String number = words.stream().map(numbers::get).collect(Collectors.joining(""));

                            System.out.println("number " + number);

                            try (CloseableHttpClient client = HttpClients.createDefault()) {
                                HttpPost httpPost = new HttpPost(
                                        "http://localhost:4567/api/v1/handlers/64a9570c-6600-4635-99a5-425946f87424/openUrl"
                                );
                                httpPost.addHeader(new BasicHeader("X-Rocket-Action-Handler-Key", "1234"));
                                httpPost.setEntity(new StringEntity("{\"text\":\"" + number + "\"}"));
                                httpPost.setHeader("Accept", "application/json");
                                httpPost.setHeader("Content-type", "application/json");
                                try (CloseableHttpResponse response = client.execute(httpPost)) {
                                    System.out.println("status: " + response.getStatusLine().getStatusCode());
                                }
                            }

                        }
                    }
//                    Desktop.getDesktop().browse(URI.create("https://google.com"));
                }
            }
//            System.out.println(recognizer.getFinalResult());
//            speakers.drain();
//            speakers.close();
//            microphone.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
