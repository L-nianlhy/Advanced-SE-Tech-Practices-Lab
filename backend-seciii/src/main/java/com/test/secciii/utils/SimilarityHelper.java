package com.test.secciii.utils;

import ai.djl.ModelException;
import ai.djl.inference.Predictor;
import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.ImageFactory;
import ai.djl.repository.zoo.ModelZoo;
import ai.djl.repository.zoo.ZooModel;
import ai.djl.translate.TranslateException;
import com.test.secciii.utils.FeatureComparison;
import com.test.secciii.utils.ImageEncoder;
import com.test.secciii.utils.TextEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SimilarityHelper {

    private static final Logger logger = LoggerFactory.getLogger(SimilarityHelper.class);

    private SimilarityHelper(){};

    public static float calculateSimilarity(String path,String text0) throws IOException, ModelException, TranslateException {

        List<String> texts = new ArrayList<>();
        texts.add(text0);

        logger.info("texts: {}", Arrays.toString(texts.toArray()));

        Path imageFile = Paths.get(path);
        Image image = ImageFactory.getInstance().fromFile(imageFile);
        logger.info("image: {}", path);

        TextEncoder sentenceEncoder = new TextEncoder();
        ImageEncoder imageEncoder = new ImageEncoder();

        //If text is chinese, isChinese = true, otherwise isChinese = false
        try (ZooModel<String, float[]> textModel = ModelZoo.loadModel(sentenceEncoder.criteria(true));
             Predictor<String, float[]> textPredictor = textModel.newPredictor();
             ZooModel<Image, float[]> imageModel = ModelZoo.loadModel(imageEncoder.criteria());
             Predictor<Image, float[]> imagePredictor = imageModel.newPredictor()) {

            float[] imageEmbeddings = imagePredictor.predict(image);
            logger.info("Vector dimension: {}", imageEmbeddings.length);
            logger.info("image embeddings: {}", Arrays.toString(imageEmbeddings));

            List<float[]> list = new ArrayList<>();
            for (String text : texts) {
                float[] textEmbedding = textPredictor.predict(text);
                list.add(textEmbedding);
            }

            float[] sims = new float[texts.size()];
            for (int i = 0; i < sims.length; i++) {
                logger.info("text [{}] embeddings: {}", texts.get(i), Arrays.toString(list.get(i)));
                sims[i] = 100 * FeatureComparison.cosineSim(imageEmbeddings, list.get(i));
                logger.info("Similarity: {}%", sims[i]);
            }

            logger.info("Label probs: {}", Arrays.toString(FeatureComparison.softmax(sims)));
            return sims[0];
        }
    }
}
