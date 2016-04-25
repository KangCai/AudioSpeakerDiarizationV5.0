package bases;


import java.io.Serializable;

/**
 * 
 * @author Ganesh Tiwari for storing all coeffs of spectral features<br>
 *         include mfcc + delta mfcc + delta delta mfcc include engergy + delta
 *         energy+ delta delta energy
 */
public class FeatureVector implements Serializable {

	/**
	 * 2d array of feature vector, dimension=noOfFrame*noOfFeatures
	 */
	private double[] rowFeature;
	private double[][] featureVector;// all
	private int noOfFrames;
	private int noOfFeatures;

	public FeatureVector() {
	}

	public double[] getRowFeature() {
		//System.out.println(featureVector.length + " " + featureVector[0].length);
		rowFeature = new double[featureVector[0].length];
		for(int j = 0; j < featureVector[0].length; j++) {
			rowFeature[j] = 0;
			for(int i = 0; i < featureVector.length; i++) {
				rowFeature[j] += featureVector[i][j];
			}
			rowFeature[j] /= featureVector.length;
		}
		return rowFeature;
	}

	public int getNoOfFrames() {
		return featureVector.length;
	}

	public void setNoOfFrames(int noOfFrames) {
		this.noOfFrames = noOfFrames;
	}

	public int getNoOfFeatures() {
		return featureVector[0].length;
	}

	public void setNoOfFeatures(int noOfFeatures) {
		this.noOfFeatures = noOfFeatures;
	}

	/**
	 * returns feature vector
	 * 
	 * @return
	 */
	public double[][] getFeatureVector() {
		return featureVector;
	}

	/**
	 * sets the feature vector array
	 * 
	 * @param featureVector
	 */
	public void setFeatureVector(double[][] featureVector) {
		this.featureVector = featureVector;
	}
}
