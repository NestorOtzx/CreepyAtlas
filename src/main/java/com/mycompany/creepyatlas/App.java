package com.mycompany.creepyatlas;

import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.system.MemoryUtil.*;

public class App {
    public static void main(String[] args) throws Exception {
        long device = alcOpenDevice((ByteBuffer) null);
        if (device == NULL) throw new IllegalStateException("Failed to open audio device.");
        ALCCapabilities caps = ALC.createCapabilities(device);
        long context = alcCreateContext(device, (int[]) null);
        alcMakeContextCurrent(context);
        AL.createCapabilities(caps);

        URL url = App.class.getResource("/plankton.wav");
        if (url == null) throw new IOException("File /plankton.wav not found in src/main/resources/");
        try (InputStream is = url.openStream();
             AudioInputStream ais0 = AudioSystem.getAudioInputStream(is)) {

            AudioFormat base = ais0.getFormat();
            AudioFormat pcm16 = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    base.getSampleRate(),
                    16,
                    base.getChannels(),
                    base.getChannels() * 2,
                    base.getSampleRate(),
                    false
            );
            try (AudioInputStream ais = AudioSystem.getAudioInputStream(pcm16, ais0)) {
                byte[] bytes = ais.readAllBytes();
                int channels = pcm16.getChannels();
                int sampleRate = Math.round(pcm16.getSampleRate());

                int format = (channels == 1) ? AL_FORMAT_MONO16 :
                             (channels == 2) ? AL_FORMAT_STEREO16 :
                             0;
                if (format == 0) throw new IllegalStateException("Unsupported channel count: " + channels);

                int buffer = alGenBuffers();
                ByteBuffer data = memAlloc(bytes.length).put(bytes);
                data.flip();
                alBufferData(buffer, format, data, sampleRate);
                memFree(data);

                int source = alGenSources();
                alSourcei(source, AL_BUFFER, buffer);

                alSourcePlay(source);
                System.out.println("Playing WAV...");

                int state;
                do {
                    state = alGetSourcei(source, AL_SOURCE_STATE);
                    Thread.sleep(50);
                } while (state == AL_PLAYING);

                alDeleteSources(source);
                alDeleteBuffers(buffer);
            }
        }

        alcDestroyContext(context);
        alcCloseDevice(device);
        System.out.println("Finished.");
    }
}
