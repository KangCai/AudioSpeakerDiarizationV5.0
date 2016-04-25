package utils;

import java.io.File;

import bases.AutocorrellatedVoiceActivityDetector;
import bases.FeatureExtract;
import bases.Normalizer;
import bases.PreProcess;
import bases.WavFile;

public class BasicFeatureExtraction {
	private final float sampleRate = 16000f;
	/**
	 * Lpc feature feature extraction
	 */
	public double[] extractFeatures(double[] voicestream, int mfccnum, int lpcnum) {
		//Normalizer normalizer = new Normalizer();
		//normalizer.normalize(voicestream, sampleRate);
		double[] mfcc = extractMfcc(voicestream);
		double[] lpc = extractLpc(voicestream);
		double[] feature = new double[mfccnum + lpcnum];
		System.arraycopy(mfcc, 0, feature, 0, mfccnum);
		System.arraycopy(lpc, 0, feature, mfccnum, lpcnum);
		return feature;
	}
	public double[] extractLpc(double[] voiceSample) {
		// Preprocess
        AutocorrellatedVoiceActivityDetector voiceDetector = new AutocorrellatedVoiceActivityDetector();
        voiceSample = voiceDetector.removeSilence(voiceSample, sampleRate);
        // lpc
        double[] lpcFeatures = new LpcFeaturesExtractor(sampleRate, 30).extractFeatures(voiceSample);
        return lpcFeatures;
    }	

	/**
	 * MFCC feature feature extraction
	 */
	public double[] extractMfcc(double[] voiceSample) {
		// Preprocess
		float[] floatArray = new float[voiceSample.length];
		for (int i = 0 ; i < voiceSample.length; i++) 
		    floatArray[i] = (float) voiceSample[i];
		PreProcess prep = new PreProcess(floatArray, 512, (int)sampleRate);
		FeatureExtract fe = new FeatureExtract(prep.getFramedSignal(), (int)sampleRate, 512);
		fe.makeMfccFeatureVector();
		double[] mfccFeatures = fe.getFeatureVector().getRowFeature();
        return mfccFeatures;
    }	

	
}
