package com.mycompany.creepyatlas;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.system.MemoryUtil.memAlloc;
import static org.lwjgl.system.MemoryUtil.memFree;

public class AudioSource3D {
    private int source;
    private int buffer;

    public AudioSource3D(String resourcePath) throws Exception {
        URL url = AudioSource3D.class.getResource(resourcePath);
        if (url == null) throw new IOException("File " + resourcePath + " not found in resources");

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
                             (channels == 2) ? AL_FORMAT_STEREO16 : 0;
                if (format == 0) throw new IllegalStateException("Unsupported channel count: " + channels);

                buffer = alGenBuffers();
                ByteBuffer data = memAlloc(bytes.length).put(bytes);
                data.flip();
                alBufferData(buffer, format, data, sampleRate);
                memFree(data);

                source = alGenSources();
                alSourcei(source, AL_BUFFER, buffer);
                alSourcef(source, AL_GAIN, 1f);
            }
        }
    }

    public void setPosition(float x, float y, float z) {
        alSource3f(source, AL_POSITION, x, y, z);
    }

    public void play() {
        alSourcePlay(source);
    }

    public boolean isPlaying() {
        return alGetSourcei(source, AL_SOURCE_STATE) == AL_PLAYING;
    }

    public void stop() {
        alSourceStop(source);
    }

    public void cleanup() {
        alDeleteSources(source);
        alDeleteBuffers(buffer);
    }
}
