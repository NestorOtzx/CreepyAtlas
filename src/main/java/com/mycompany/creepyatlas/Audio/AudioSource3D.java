package com.mycompany.creepyatlas.Audio;

import javax.sound.sampled.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.system.MemoryUtil.memAlloc;
import static org.lwjgl.system.MemoryUtil.memFree;

public class AudioSource3D {
    private int openAlSourceId;
    private int openAlBufferId;
    private boolean playAudioInLoop;

    private float positionX;
    private float positionY;

    private boolean isEnabled = true;

    public AudioSource3D(String resourcePath, boolean loop, int x, int y) throws Exception {
        this.playAudioInLoop = loop;
        URL resourceUrl = AudioSource3D.class.getResource(resourcePath);
        if (resourceUrl == null) throw new IOException("File " + resourcePath + " not found in resources");

        try (InputStream stream = resourceUrl.openStream(); AudioInputStream originalStream = AudioSystem.getAudioInputStream(stream)) {
            AudioFormat baseFormat = originalStream.getFormat();
            AudioFormat pcm16Format = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    baseFormat.getSampleRate(),
                    16,
                    baseFormat.getChannels(),
                    baseFormat.getChannels() * 2,
                    baseFormat.getSampleRate(),
                    false
            );


            try (AudioInputStream pcmStream = AudioSystem.getAudioInputStream(pcm16Format, originalStream)) {
                byte[] audioBytes = pcmStream.readAllBytes();
                int channelCount = pcm16Format.getChannels();
                int sampleRateHz = Math.round(pcm16Format.getSampleRate());

                int audioFormat = (channelCount == 1) ? AL_FORMAT_MONO16 :
                                  (channelCount == 2) ? AL_FORMAT_STEREO16 : 0;
                if (audioFormat == 0) throw new IllegalStateException("Unsupported channel count: " + channelCount);

                openAlBufferId = alGenBuffers();
                ByteBuffer audioBuffer = memAlloc(audioBytes.length).put(audioBytes);
                audioBuffer.flip();
                alBufferData(openAlBufferId, audioFormat, audioBuffer, sampleRateHz);
                memFree(audioBuffer);

                openAlSourceId = alGenSources();
                alSourcei(openAlSourceId, AL_BUFFER, openAlBufferId);
                alSourcef(openAlSourceId, AL_GAIN, 1f);
                alSourcei(openAlSourceId, AL_LOOPING, loop ? AL_TRUE : AL_FALSE);
            }catch (Exception exception){
            }
        }
        catch (Exception exception){
        }

        setPosition(x, y);
        AudioListener3D.registerSource(this);
    }

    public void setPlayAudioInLoop(boolean loop) {
        this.playAudioInLoop = loop;
        alSourcei(openAlSourceId, AL_LOOPING, loop ? AL_TRUE : AL_FALSE);
    }

    public boolean isLooping() {
        return playAudioInLoop;
    }

    public void setPosition(float x, float y) {
        this.positionX = x * AudioListener3D.SPACE_UNITS;
        this.positionY = y * AudioListener3D.SPACE_UNITS;
        alSource3f(openAlSourceId, AL_POSITION, this.positionX, this.positionY, 0);
    }

    public void setGain(float gain) {
        if (gain <= 0 || gain > 0 && isEnabled)
        {
            alSourcef(openAlSourceId, AL_GAIN, gain);
        }
    }

    public void disable(){
        setGain(0);
        isEnabled = false;
    }

    public void enable(){
        isEnabled = true;
    }

    public float getPositionX() { return positionX; }
    public float getPositionY() { return positionY; }

    public void play() {
        if (isEnabled)
        {
            alSourcePlay(openAlSourceId);
        }
    }

    public boolean isPlaying() {
        return alGetSourcei(openAlSourceId, AL_SOURCE_STATE) == AL_PLAYING;
    }

    public void stop() {
        alSourceStop(openAlSourceId);
    }

    public void cleanup() {
        alDeleteSources(openAlSourceId);
        alDeleteBuffers(openAlBufferId);
    }
}
