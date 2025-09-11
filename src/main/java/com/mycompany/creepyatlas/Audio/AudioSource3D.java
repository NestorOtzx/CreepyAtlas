package com.mycompany.creepyatlas.Audio;

import javax.sound.sampled.*;

import com.mycompany.creepyatlas.Enums.Enums.AudioEffectType;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.openal.EXTEfx.*;
import static org.lwjgl.system.MemoryUtil.memAlloc;
import static org.lwjgl.system.MemoryUtil.memFree;

public class AudioSource3D {

    

    private int openAlSourceId;
    private int openAlBufferId;
    private int effectSlotId;
    private int effectId;

    private boolean playAudioInLoop;
    private boolean isEnabled = true;

    private float positionX;
    private float positionY;

    public AudioSource3D(String resourcePath, boolean loop, int x, int y, AudioEffectType effectType) throws Exception {
        this.playAudioInLoop = loop;
        URL resourceUrl = AudioSource3D.class.getResource(resourcePath);
        if (resourceUrl == null) throw new IOException("File " + resourcePath + " not found in resources");

        try (InputStream stream = resourceUrl.openStream();
             AudioInputStream originalStream = AudioSystem.getAudioInputStream(stream)) {

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
            }
        }

        if (effectType != AudioEffectType.NONE) {
            initEffect(effectType);
        }

        setPosition(x, y);
        AudioListener3D.registerSource(this);
    }

    private void initEffect(AudioEffectType effectType) {
        effectId = alGenEffects();

        switch (effectType) {
            case REVERB -> {
                alEffecti(effectId, AL_EFFECT_TYPE, AL_EFFECT_REVERB);

                // Parámetros del reverb (más intensos)
                alEffectf(effectId, AL_REVERB_GAIN, 1.0f);
                alEffectf(effectId, AL_REVERB_GAINHF, 1.0f);
                alEffectf(effectId, AL_REVERB_DECAY_TIME, 7.0f);
                alEffectf(effectId, AL_REVERB_REFLECTIONS_GAIN, 0.8f);
                alEffectf(effectId, AL_REVERB_REFLECTIONS_DELAY, 0.05f);
                alEffectf(effectId, AL_REVERB_LATE_REVERB_GAIN, 1.0f);
                alEffectf(effectId, AL_REVERB_LATE_REVERB_DELAY, 0.1f);
            }
            case ECHO -> {
                alEffecti(effectId, AL_EFFECT_TYPE, AL_EFFECT_ECHO);

                // Parámetros del eco
                alEffectf(effectId, AL_ECHO_DELAY, 0.5f);
                alEffectf(effectId, AL_ECHO_LRDELAY, 0.2f);
                alEffectf(effectId, AL_ECHO_DAMPING, 0.5f);
                alEffectf(effectId, AL_ECHO_FEEDBACK, 0.7f);
                alEffectf(effectId, AL_ECHO_SPREAD, -1.0f);
            }
            case NONE ->{
                
            }
        }

        // Crear slot para el efecto
        effectSlotId = alGenAuxiliaryEffectSlots();
        alAuxiliaryEffectSloti(effectSlotId, AL_EFFECTSLOT_EFFECT, effectId);

        // Conectar el source al slot (simple, sin filtros extra)
        alSourcei(openAlSourceId, AL_AUXILIARY_SEND_FILTER, effectSlotId);
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
        if (gain <= 0 || (gain > 0 && isEnabled)) {
            alSourcef(openAlSourceId, AL_GAIN, gain);
        }
    }

    public void disable() {
        setGain(0);
        isEnabled = false;
    }

    public void enable() {
        isEnabled = true;
    }

    public float getPositionX() { return positionX; }
    public float getPositionY() { return positionY; }

    public void play() {
        if (isEnabled) {
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

        if (effectSlotId != 0) {
            alDeleteAuxiliaryEffectSlots(effectSlotId);
        }
        if (effectId != 0) {
            alDeleteEffects(effectId);
        }
    }
}
