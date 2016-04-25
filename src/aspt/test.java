package aspt;

import java.io.File;
import java.io.IOException;

import bases.WavFileException;
import utils.BasicClassification;


public class test {
	public static void main(String[] args) throws IOException, WavFileException {
		double wintime = 0.4;
		int mfccnum = 15, lpcnum = 15;
		BasicFE fe = new BasicFE();		
		BasicClassification bc = new BasicClassification();
		String basefile = "/Users/karl/Work/database/speaker/ubm/train.libsvm";
		String trainfile = "/Users/karl/Work/database/speaker/ubm/specifictrain.libsvm";
		String testfile = "/Users/karl/Work/database/speaker/ubm/test.libsvm";
		String modelfile =  "/Users/karl/Work/database/speaker/ubm/modelfile";
		
		
		/*
		String[] dirsneg = {"/Users/karl/Work/database/speaker/ubm/"};
		double[][] featureNeg = fe.getFeatureMatrix(dirsneg, wintime, mfccnum, lpcnum);
		fe.featureMatrixToLibsvm(featureNeg, trainfile, mfccnum + lpcnum, "-1", false);
		System.out.println(featureNeg.length);
		 
		
		//拷贝train.libsvm，然后再在新文件后尾部添加，作为trainfile
		//测试不去噪的效果
		String[] dirspos = {"/Users/karl/Work/database/download/speakertest/ctrain/"};
		//String[] dirspos = {"/Users/karl/Work/database/download/speakerdiarization/c/"};
		double[][] featurePos = fe.getFeatureMatrix(dirspos, wintime, mfccnum, lpcnum);
		double[][] repmat = fe.repmat(featurePos, 3);
		fe.copyFile(basefile, trainfile);
		fe.featureMatrixToLibsvm(repmat, trainfile, mfccnum + lpcnum, "1", true);
		System.out.println(featurePos.length + " " + repmat.length);
		 
		bc.libtraining(trainfile, modelfile);
		*/
		
		String dir = "/Users/karl/Work/database/download/speakertest/c/";
		//String dir = "/Users/karl/Work/database/download/speakerdiarization/all/";
		File file = new File(dir);
		String[] names = file.list();
		int total = 0, pos = 0;
		for(String str : names) {
			String dirstest = dir + str;
			if(str.length() < 4)
				continue;
			if(str.substring(str.length() - 4).equals(".wav")) {
				System.out.println(dirstest);
				double[][] featurePosTest = fe.getFeatureMatrix(dirstest, wintime, mfccnum, lpcnum);
				fe.featureMatrixToLibsvm(featurePosTest, testfile, mfccnum + lpcnum, "1", false);
				if(bc.libpredicting(testfile, modelfile) >= 0.5) {
					System.out.println("PASS");
					pos++;
				}
				else
					System.out.println("NOT PASS");
				total++;
			}
		}
		System.out.println(pos * 1.0 / total);
	}
}
