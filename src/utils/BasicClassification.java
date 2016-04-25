package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class BasicClassification {
	
	public void libtraining(String trainFile, String mf) {
		String[] arg = { "-b", "1", "-t", "0", trainFile, // 存放SVM训练模型用的数据的路径
				mf }; // 存放SVM通过训练数据训/ //练出来的模型的路径
		// 创建一个训练对象
		svm_train t = new svm_train();
		try {
			t.main(arg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 调用
	}
	public double libpredicting(String predictFile, String mf) {
		int pos = 0, total = 0;
		String[] parg = { "-b", "1", predictFile, // 这个是存放测试数据
				mf, // 调用的是训练以后的模型
				mf + "result" }; // 生成的结果的文件的路径
		// 创建一个预测或者分类的对象
		svm_predict p = new svm_predict();
		try {
			p.main(parg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		File file = new File(mf + "result");
		if(!file.exists())
			file.createNewFile();
			*/
		BufferedReader reader;
		try {
			reader = new BufferedReader(
					new FileReader(mf + "result"));
			reader.readLine();
			String contents;
			String[] result = null;
			while((contents = reader.readLine()) != null) {
				total++;
				 result =  contents.split(" ");
				 if(result[0].equals("1.0"))
					 pos++;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 创建一个预测或者分类的对象
		return (double)(pos * 1.0 / total);
	}

}
