package aspt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import bases.WavFile;
import utils.BasicClassification;
import utils.BasicFeatureExtraction;

public class BasicFE {
	int samplerate = 16000;
	public double[][] getFeatureMatrix (String[] dirs, double longwin, int mfccnum, int lpcnum) {
		ArrayList<String> filenames = getAllWavnames(dirs);
		ArrayList<double[] > fm = new ArrayList<double[]>();
		int winlen = (int)(samplerate * longwin);
		BasicFeatureExtraction bfe  = new BasicFeatureExtraction();
		for(String str : filenames) {
			double[] wavstream = convertFileToDoubleArray(str);
			double[] winstream = new double[winlen];
			int len = wavstream.length, winnum = (int)(len * 2 / winlen) - 1;
			for(int i = 0; i < winnum; i++) {
				System.arraycopy(wavstream, (int)(i * winlen / 2), winstream, 0, winlen);
				double[] feature = bfe.extractFeatures(winstream, mfccnum, lpcnum);
				if(feature[mfccnum + lpcnum - 1] != 0.0)
					fm.add(feature);
			}
		}
		double[][] featureMatrix = new double[fm.size()][mfccnum + lpcnum];
		for(int i = 0; i < fm.size(); i++) 
			featureMatrix[i] = fm.get(i);
		return featureMatrix;
	}
	public double[][] getFeatureMatrix (String str, double longwin, int mfccnum, int lpcnum) {
		ArrayList<double[] > fm = new ArrayList<double[]>();
		int winlen = (int)(samplerate * longwin);
		BasicFeatureExtraction bfe  = new BasicFeatureExtraction();
		double[] wavstream = convertFileToDoubleArray(str);
		double[] winstream = new double[winlen];
		int len = wavstream.length, winnum = (int)(len * 2 / winlen) - 1;
		for(int i = 0; i < winnum; i++) {
			System.arraycopy(wavstream, (int)(i * winlen / 2), winstream, 0, winlen);
			double[] feature = bfe.extractFeatures(winstream, mfccnum, lpcnum);
			if(feature[mfccnum + lpcnum - 1] != 0.0)
				fm.add(feature);
		}
		double[][] featureMatrix = new double[fm.size()][mfccnum + lpcnum];
		for(int i = 0; i < fm.size(); i++) 
			featureMatrix[i] = fm.get(i);
		return featureMatrix;
	}
	private ArrayList<String> getAllWavnames(String[] dirs) {
		ArrayList<String> filenames = new ArrayList<String>();
		for(int i = 0; i < dirs.length; i++) {
			File file = new File(dirs[i]);
			String[] names = file.list();
			for(int j = 0; j < names.length; j++) {
				if(names[j].length() < 4)
					continue;
				if(names[j].substring(names[j].length() - 4, names[j].length()).equals(".wav")) 
					filenames.add(dirs[i] + names[j]);
			}
		}
		return filenames;
	}
	private double[] convertFileToDoubleArray(String voiceSampleFile) {
		WavFile wavFile;
		try {
			File file = new File(voiceSampleFile);
			wavFile = WavFile.openWavFile(file);
			int nframes = (int) wavFile.getNumFrames();
			double[] buffer = new double[nframes];
			wavFile.readFrames(buffer, nframes);
			return buffer;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void featureMatrixToLibsvm(double[][] featureMatrix, String savefile, 
			int featureDim, String label, boolean tail) {		
		StringBuffer sb = new StringBuffer();		
		//numeric  
		for(int i = 0; i < featureMatrix.length; i++) {
			sb.append(label);
			for(int j = 0; j < featureDim; j++) 
				sb.append(" " + (j + 1) + ":" + featureMatrix[i][j]);
			sb.append('\n');
		}
		try {
			FileWriter fw = new FileWriter(new File(savefile), tail);
			fw.write(sb.toString());
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void featureMatrixToLibsvm(double[] featureMatrix, String savefile, 
			int featureDim, String label, boolean tail) {		
		StringBuffer sb = new StringBuffer();		
		//numeric  
		sb.append(label);
		for(int j = 0; j < featureDim; j++) 
			sb.append(" " + (j + 1) + ":" + featureMatrix[j]);
		sb.append('\n');
		try {
			FileWriter fw = new FileWriter(new File(savefile), tail);
			fw.write(sb.toString());
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public double[][] repmat (double[][] featureMatrix, int repeatnum) {
		int len = featureMatrix.length;
		double[][] repmat = new double[len * repeatnum][featureMatrix[0].length];
		for(int i = 0; i < repeatnum; i++) 
			for(int j = 0; j < len; j++) 
				System.arraycopy(featureMatrix[j], 0, repmat[i * len + j], 0, featureMatrix[j].length); 
		return repmat;
	}
	public boolean copyFile(String srcFileName, String destFileName) {  
		boolean overlay = true;
		File srcFile = new File(srcFileName);  

		// 判断目标文件是否存在  
		File destFile = new File(destFileName);  
		if (destFile.exists()) {  
			// 如果目标文件存在并允许覆盖  
			if (overlay) {  
				// 删除已经存在的目标文件，无论目标文件是目录还是单个文件  
				new File(destFileName).delete();  
			}  
		} else {  
			// 如果目标文件所在目录不存在，则创建目录  
			if (!destFile.getParentFile().exists()) {  
				// 目标文件所在目录不存在  
				if (!destFile.getParentFile().mkdirs()) {  
					// 复制文件失败：创建目标文件所在目录失败  
					return false;  
				}  
			}  
		}  

		// 复制文件  
		int byteread = 0; // 读取的字节数  
		InputStream in = null;  
		OutputStream out = null;  

		try {  
			in = new FileInputStream(srcFile);  
			out = new FileOutputStream(destFile);  
			byte[] buffer = new byte[1024];  

			while ((byteread = in.read(buffer)) != -1) {  
				out.write(buffer, 0, byteread);  
			}  
			return true;  
		} catch (IOException e) {  
			return false;  
		} finally {  
			try {  
				if (out != null)  
					out.close();  
				if (in != null)  
					in.close();  
			} catch (IOException e) {  
				e.printStackTrace();  
			}  
		}  
	}
}
