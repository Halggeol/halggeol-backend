package com.halggeol.backend.recommend.service.calculater;

import java.util.List;

public class SimilarityCalculator {

    public static double cosineSimilarity(List<Double> vec1, List<Double> vec2) {
        double dot = 0.0;
        double normA = 0.0;
        double normB = 0.0;

        for (int i = 0; i < vec1.size(); i++) {
            double a = vec1.get(i);
            double b = vec2.get(i);
            dot += a * b;
            normA += a * a;
            normB += b * b;
        }

        return (normA == 0 || normB == 0) ? 0.0 : dot / (Math.sqrt(normA) * Math.sqrt(normB));
    }
}
