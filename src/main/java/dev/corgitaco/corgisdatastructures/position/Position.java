package dev.corgitaco.corgisdatastructures.position;

import java.util.Arrays;

public interface Position {

    double x();
    double y();
    double z();

    default double getX() {
        return x();
    }

    default double getY() {
        return y();
    }

    default double getZ() {
        return z();
    }

    default int floorX() {
        return (int) Math.floor(x());
    }

    default int floorY() {
        return (int) Math.floor(y());
    }

    default int floorZ() {
        return (int) Math.floor(z());
    }

    default Position floor() {
        return create((int) Math.floor(x()), (int) Math.floor(y()), (int) Math.floor(z()));
    }

    Position as2D();

    Position create(double x, double y, double z);

    default Position add(double x, double y, double z) {
        return create(this.x() + x, this.y() + y, this.z() + z);
    }

    default Position add(Position position) {
        return add(position.x(), position.y(), position.z());
    }

    default Position subtract(double x, double y, double z) {
        return create(this.x() - x, this.y() - y, this.z() - z);
    }

    default Position subtract(Position position) {
        return subtract(position.x(), position.y(), position.z());
    }

    default Position multiply(double x, double y, double z) {
        return create(this.x() * x, this.y() * y, this.z() * z);
    }

    default Position multiply(Position position) {
        return multiply(position.x(), position.y(), position.z());
    }

    default Position divide(double x, double y, double z) {
        return create(this.x() / x, this.y() / y, this.z() / z);
    }

    default Position divide(Position position) {
        return divide(position.x(), position.y(), position.z());
    }

    // Default method to calculate Euclidean distance between two Positions in 3D space
    default double euclideanDistance(Position other) {
        double dx = this.getX() - other.getX();
        double dy = this.getY() - other.getY();
        double dz = this.getZ() - other.getZ();
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    // Default method to calculate Manhattan distance between two Positions in 3D space
    default double manhattanDistance(Position other) {
        return Math.abs(this.getX() - other.getX()) +
                Math.abs(this.getY() - other.getY()) +
                Math.abs(this.getZ() - other.getZ());
    }


    // Default method to calculate Minkowski distance with order p between two Positions in 3D space
    default double minkowskiDistance(Position other, double p) {
        double dx = Math.abs(this.getX() - other.getX());
        double dy = Math.abs(this.getY() - other.getY());
        double dz = Math.abs(this.getZ() - other.getZ());
        return Math.pow(Math.pow(dx, p) + Math.pow(dy, p) + Math.pow(dz, p), 1 / p);
    }

    // Default method to calculate squared Euclidean distance (no sqrt)
    default double squaredEuclideanDistance(Position other) {
        double dx = this.getX() - other.getX();
        double dy = this.getY() - other.getY();
        double dz = this.getZ() - other.getZ();
        return dx * dx + dy * dy + dz * dz;
    }

    default double squaredHorizontalEuclideanDistance(Position other) {
        double dx = this.getX() - other.getX();
        double dz = this.getZ() - other.getZ();
        return dx * dx + dz * dz;
    }

    // Default method to calculate the squared Manhattan distance
    default int squaredManhattanDistance(Position other) {
        return (int) Math.pow(this.getX() - other.getX(), 2) +
                (int) Math.pow(this.getY() - other.getY(), 2) +
                (int) Math.pow(this.getZ() - other.getZ(), 2);
    }

    // Default method to calculate Cosine similarity between two Positions in 3D space
    default double cosineSimilarity(Position other) {
        double dotProduct = this.getX() * other.getX() + this.getY() * other.getY() + this.getZ() * other.getZ();
        double magnitudeA = Math.sqrt(this.getX() * this.getX() + this.getY() * this.getY() + this.getZ() * this.getZ());
        double magnitudeB = Math.sqrt(other.getX() * other.getX() + other.getY() * other.getY() + other.getZ() * other.getZ());
        return dotProduct / (magnitudeA * magnitudeB);
    }


    // Default method to calculate Jaccard Similarity for binary data (using presence/absence representation)
    default double jaccardSimilarity(Position other) {
        int intersection = 0;
        int union = 0;

        if (this.getX() > 0 && other.getX() > 0) intersection++;
        if (this.getY() > 0 && other.getY() > 0) intersection++;
        if (this.getZ() > 0 && other.getZ() > 0) intersection++;

        if (this.getX() > 0 || other.getX() > 0) union++;
        if (this.getY() > 0 || other.getY() > 0) union++;
        if (this.getZ() > 0 || other.getZ() > 0) union++;

        return (double) intersection / union;
    }

    // Default method to calculate Bray-Curtis Dissimilarity between two Positions in 3D space
    default double brayCurtisDissimilarity(Position other) {
        double sumAbs = Math.abs(this.getX()) + Math.abs(this.getY()) + Math.abs(this.getZ()) + Math.abs(other.getX()) + Math.abs(other.getY()) + Math.abs(other.getZ());
        double sumDiff = Math.abs(this.getX() - other.getX()) + Math.abs(this.getY() - other.getY()) + Math.abs(this.getZ() - other.getZ());
        return sumDiff / sumAbs;
    }

    // Default method to calculate Relative Euclidean Distance between two Positions
    default double relativeEuclideanDistance(Position other) {
        double normA = Math.sqrt(this.getX() * this.getX() + this.getY() * this.getY() + this.getZ() * this.getZ());
        double normB = Math.sqrt(other.getX() * other.getX() + other.getY() * other.getY() + other.getZ() * other.getZ());
        return euclideanDistance(other) / Math.max(normA, normB);
    }

    // Default method to calculate Weighted Euclidean distance (with weights wX, wY, wZ)
    default double weightedEuclideanDistance(Position other, double wX, double wY, double wZ) {
        double dx = this.getX() - other.getX();
        double dy = this.getY() - other.getY();
        double dz = this.getZ() - other.getZ();
        return Math.sqrt(wX * dx * dx + wY * dy * dy + wZ * dz * dz);
    }

    // Default method to calculate Logarithmic Distance
    default double logarithmicDistance(Position other) {
        double dx = Math.log(1 + Math.abs(this.getX() - other.getX()));
        double dy = Math.log(1 + Math.abs(this.getY() - other.getY()));
        double dz = Math.log(1 + Math.abs(this.getZ() - other.getZ()));
        return dx + dy + dz;
    }

    // Default method to calculate Earth Mover’s Distance (EMD)
    default double earthMoversDistance(Position other) {
        // For simplicity, let's assume this is a simple Euclidean distance for now, since full EMD calculation requires solving an optimization problem
        return euclideanDistance(other);
    }

    // Default method to calculate Pearson Correlation Coefficient between two Positions
    default double pearsonCorrelation(Position other) {
        double meanA = (this.getX() + this.getY() + this.getZ()) / 3.0;
        double meanB = (other.getX() + other.getY() + other.getZ()) / 3.0;

        double numerator = (this.getX() - meanA) * (other.getX() - meanB) +
                (this.getY() - meanA) * (other.getY() - meanB) +
                (this.getZ() - meanA) * (other.getZ() - meanB);

        double denominator = Math.sqrt(Math.pow(this.getX() - meanA, 2) + Math.pow(this.getY() - meanA, 2) + Math.pow(this.getZ() - meanA, 2)) *
                Math.sqrt(Math.pow(other.getX() - meanB, 2) + Math.pow(other.getY() - meanB, 2) + Math.pow(other.getZ() - meanB, 2));

        return numerator / denominator;
    }

    // Default method to calculate Spearman's Rank Correlation
    default double spearmanRankCorrelation(Position other) {
        // Simplified version assuming ranks are based on the original values
        double[] a = {this.getX(), this.getY(), this.getZ()};
        double[] b = {other.getX(), other.getY(), other.getZ()};

        // Rank the values
        Arrays.sort(a);
        Arrays.sort(b);

        double dSquaredSum = 0;
        for (int i = 0; i < 3; i++) {
            dSquaredSum += Math.pow(a[i] - b[i], 2);
        }

        return 1 - (6 * dSquaredSum) / (3 * (3 * 3 - 1));
    }

    // Default method to calculate Hamming Distance for multi-dimensional data
    default int hammingDistanceMultiDim(Position other) {
        int distance = 0;
        if (this.getX() != other.getX()) distance++;
        if (this.getY() != other.getY()) distance++;
        if (this.getZ() != other.getZ()) distance++;
        return distance;
    }

    // Default method to calculate Tanimoto Coefficient (similar to Jaccard but for numeric data)
    default double tanimotoCoefficient(Position other) {
        double dotProduct = this.getX() * other.getX() + this.getY() * other.getY() + this.getZ() * other.getZ();
        double normA = this.getX() * this.getX() + this.getY() * this.getY() + this.getZ() * this.getZ();
        double normB = other.getX() * other.getX() + other.getY() * other.getY() + other.getZ() * other.getZ();
        return dotProduct / (normA + normB - dotProduct);
    }

    // Default method to calculate Squared Tanimoto Coefficient
    default double squaredTanimotoCoefficient(Position other) {
        double tanimoto = tanimotoCoefficient(other);
        return tanimoto * tanimoto;
    }

    // Default method to calculate Kullback-Leibler Divergence (requires normalized probability distributions)
    default double kullbackLeiblerDivergence(Position other) {
        double pX = (this.getX() + 1.0) / (this.getX() + other.getX() + 2.0);  // Simple probability estimation
        double pY = (this.getY() + 1.0) / (this.getY() + other.getY() + 2.0);
        double pZ = (this.getZ() + 1.0) / (this.getZ() + other.getZ() + 2.0);
        double qX = (other.getX() + 1.0) / (this.getX() + other.getX() + 2.0);
        double qY = (other.getY() + 1.0) / (this.getY() + other.getY() + 2.0);
        double qZ = (other.getZ() + 1.0) / (this.getZ() + other.getZ() + 2.0);

        return pX * Math.log(pX / qX) + pY * Math.log(pY / qY) + pZ * Math.log(pZ / qZ);
    }

    // Default method to calculate Bhattacharyya Distance
    default double bhattacharyyaDistance(Position other) {
        double pX = this.getX() / (double)(this.getX() + other.getX());
        double pY = this.getY() / (double)(this.getY() + other.getY());
        double pZ = this.getZ() / (double)(this.getZ() + other.getZ());
        return -Math.log(pX * pY * pZ);
    }

    // Default method to calculate Tchebychev distance between two Positions in 3D space
    default double tchebychevDistance(Position other) {
        return Math.max(Math.abs(this.getX() - other.getX()),
                Math.max(Math.abs(this.getY() - other.getY()),
                        Math.abs(this.getZ() - other.getZ())));
    }

    // Default method to calculate L1 norm (Manhattan Distance)
    default double l1Norm(Position other) {
        return manhattanDistance(other);
    }

    // Default method to calculate L2 norm (Euclidean Distance)
    default double l2Norm(Position other) {
        return euclideanDistance(other);
    }

    // Default method to calculate Geodesic Distance on a Sphere (Assuming a unit sphere)
    default double geodesicDistance(Position other) {
        double lat1 = Math.toRadians(this.getX());
        double lon1 = Math.toRadians(this.getY());
        double lat2 = Math.toRadians(other.getX());
        double lon2 = Math.toRadians(other.getY());

        double dLat = lat2 - lat1;
        double dLon = lon2 - lon1;

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(lat1) * Math.cos(lat2) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Radius of the sphere (Earth in kilometers)
        double radius = 6371.0;
        return radius * c;
    }

    // Default method to calculate Jensen-Shannon Divergence (probabilistic distance)
    default double jensenShannonDivergence(Position other) {
        double pX = (this.getX() + 1.0) / (this.getX() + other.getX() + 2.0);  // Simple probability estimation
        double pY = (this.getY() + 1.0) / (this.getY() + other.getY() + 2.0);
        double pZ = (this.getZ() + 1.0) / (this.getZ() + other.getZ() + 2.0);

        double qX = (other.getX() + 1.0) / (this.getX() + other.getX() + 2.0);
        double qY = (other.getY() + 1.0) / (this.getY() + other.getY() + 2.0);
        double qZ = (other.getZ() + 1.0) / (this.getZ() + other.getZ() + 2.0);

        double mX = 0.5 * (pX + qX);
        double mY = 0.5 * (pY + qY);
        double mZ = 0.5 * (pZ + qZ);

        double klP = pX * Math.log(pX / mX) + pY * Math.log(pY / mY) + pZ * Math.log(pZ / mZ);
        double klQ = qX * Math.log(qX / mX) + qY * Math.log(qY / mY) + qZ * Math.log(qZ / mZ);

        return 0.5 * (klP + klQ);
    }

    // Default method to calculate Fréchet Distance (used for shape matching)
    default double frechetDistance(Position other) {
        double dx = this.getX() - other.getX();
        double dy = this.getY() - other.getY();
        double dz = this.getZ() - other.getZ();
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    // Default method to calculate Gower’s Distance (used for mixed data types)
    default double gowerDistance(Position other) {
        // Here, we assume the data is continuous and the distance is Euclidean
        double dx = Math.abs(this.getX() - other.getX());
        double dy = Math.abs(this.getY() - other.getY());
        double dz = Math.abs(this.getZ() - other.getZ());
        return (dx + dy + dz) / 3.0;
    }

    // Default method to calculate Wasserstein Distance (Earth Mover's Distance)
    default double wassersteinDistance(Position other) {
        double dx = Math.abs(this.getX() - other.getX());
        double dy = Math.abs(this.getY() - other.getY());
        double dz = Math.abs(this.getZ() - other.getZ());
        return dx + dy + dz; // For simplicity, treating this as Manhattan Distance
    }

    // Default method to calculate Hausdorff Distance
    default double hausdorffDistance(Position other) {
        // This method calculates the maximum of the minimum distances from each Position in one set to the other set.
        // In this case, we assume that both Positions are singular and calculate the standard distance.

        // For simplicity, we'll use the maximum Euclidean distance as a rough approximation
        double d1 = euclideanDistance(other);  // Distance from this Position to other
        double d2 = other.euclideanDistance(this);  // Distance from other to this

        return Math.max(d1, d2);  // Max distance is the Hausdorff distance for single Positions
    }

    // Default method to calculate Torus Distance (periodic boundary conditions)
    default double torusDistance(Position other, double periodX, double periodY) {
        double dx = Math.abs(this.getX() - other.getX());
        double dy = Math.abs(this.getY() - other.getY());

        // Apply periodic boundary condition (wrap around)
        dx = Math.min(dx, periodX - dx);
        dy = Math.min(dy, periodY - dy);

        return Math.sqrt(dx * dx + dy * dy);
    }

    // Default method to calculate Mahalanobis Distance (requires covariance matrix)
    default double mahalanobisDistance(Position other, double[][] covarianceMatrix) {
        double[] vectorA = {this.getX(), this.getY(), this.getZ()};
        double[] vectorB = {other.getX(), other.getY(), other.getZ()};

        // Inverting the covariance matrix
        double[][] inverseCovariance = invertMatrix(covarianceMatrix);

        // Compute the Mahalanobis distance
        double[] diff = new double[3];
        for (int i = 0; i < 3; i++) {
            diff[i] = vectorA[i] - vectorB[i];
        }

        double sum = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                sum += diff[i] * inverseCovariance[i][j] * diff[j];
            }
        }
        return Math.sqrt(sum);
    }

    // Default method to calculate Cosine Distance (from Cosine Similarity)
    default double cosineDistance(Position other) {
        double dotProduct = this.getX() * other.getX() + this.getY() * other.getY() + this.getZ() * other.getZ();
        double magnitudeA = Math.sqrt(this.getX() * this.getX() + this.getY() * this.getY() + this.getZ() * this.getZ());
        double magnitudeB = Math.sqrt(other.getX() * other.getX() + other.getY() * other.getY() + other.getZ() * other.getZ());

        // Cosine similarity is the cosine of the angle between the vectors
        double cosineSimilarity = dotProduct / (magnitudeA * magnitudeB);

        // Cosine Distance is 1 minus cosine similarity
        return 1 - cosineSimilarity;
    }

    // Default method to calculate Canberra Distance
    default double canberraDistance(Position other) {
        double dx = (double) Math.abs(this.getX() - other.getX()) / (Math.abs(this.getX()) + Math.abs(other.getX()));
        double dy = (double) Math.abs(this.getY() - other.getY()) / (Math.abs(this.getY()) + Math.abs(other.getY()));
        double dz = (double) Math.abs(this.getZ() - other.getZ()) / (Math.abs(this.getZ()) + Math.abs(other.getZ()));
        return dx + dy + dz;
    }

    // Default method to calculate Chi-Square Distance
    default double chiSquareDistance(Position other) {
        double dx = Math.pow(this.getX() - other.getX(), 2) / (this.getX() + other.getX() + 1e-6);  // Adding small value to avoid divide by zero
        double dy = Math.pow(this.getY() - other.getY(), 2) / (this.getY() + other.getY() + 1e-6);
        double dz = Math.pow(this.getZ() - other.getZ(), 2) / (this.getZ() + other.getZ() + 1e-6);
        return dx + dy + dz;
    }

    // Default method to calculate Gini-Simpson Index
    default double giniSimpsonIndex(Position other) {
        double p1 = this.getX() / (double) (this.getX() + other.getX());
        double p2 = this.getY() / (double) (this.getY() + other.getY());
        double p3 = this.getZ() / (double) (this.getZ() + other.getZ());
        return 1 - (p1 * p1 + p2 * p2 + p3 * p3);
    }

    // Default method to calculate Sørensen-Dice Index
    default double sorensenDiceIndex(Position other) {
        double intersection = Math.abs(this.getX() - other.getX()) + Math.abs(this.getY() - other.getY()) + Math.abs(this.getZ() - other.getZ());
        return 2.0 * intersection / (this.getX() + this.getY() + this.getZ() + other.getX() + other.getY() + other.getZ());
    }

    // Default method to calculate Bhattacharyya Coefficient
    default double bhattacharyyaCoefficient(Position other) {
        double pX = (this.getX() + 1.0) / (this.getX() + other.getX() + 2.0);
        double pY = (this.getY() + 1.0) / (this.getY() + other.getY() + 2.0);
        double pZ = (this.getZ() + 1.0) / (this.getZ() + other.getZ() + 2.0);
        double qX = (other.getX() + 1.0) / (this.getX() + other.getX() + 2.0);
        double qY = (other.getY() + 1.0) / (this.getY() + other.getY() + 2.0);
        double qZ = (other.getZ() + 1.0) / (this.getZ() + other.getZ() + 2.0);
        return Math.exp(-Math.sqrt(Math.pow(pX - qX, 2) + Math.pow(pY - qY, 2) + Math.pow(pZ - qZ, 2)));
    }

    // Default method to calculate Fisher Information Distance
    default double fisherInformationDistance(Position other) {
        // Fisher information distance requires a bit more advanced setup.
        // This is a simplified version, and a real implementation requires access to probability distributions.
        double meanDiff = Math.abs(this.getX() - other.getX()) + Math.abs(this.getY() - other.getY()) + Math.abs(this.getZ() - other.getZ());
        return meanDiff / Math.sqrt(3);  // Simple version of the distance
    }

    // Default method to calculate Levenshtein (Edit) Distance between two sets of coordinates (if treated as strings)
    default int levenshteinDistance(Position other) {
        String a = this.getX() + "," + this.getY() + "," + this.getZ();
        String b = other.getX() + "," + other.getY() + "," + other.getZ();

        int m = a.length();
        int n = b.length();
        int[][] dp = new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                if (i == 0) dp[i][j] = j;
                else if (j == 0) dp[i][j] = i;
                else dp[i][j] = Math.min(Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1),
                            dp[i - 1][j - 1] + (a.charAt(i - 1) == b.charAt(j - 1) ? 0 : 1));
            }
        }
        return dp[m][n];
    }

    // Default method to calculate Jaccard Index (for set-like data)
    default double jaccardIndex(Position other) {
        int intersection = (this.getX() > 0 && other.getX() > 0 ? 1 : 0) +
                (this.getY() > 0 && other.getY() > 0 ? 1 : 0) +
                (this.getZ() > 0 && other.getZ() > 0 ? 1 : 0);

        int union = (this.getX() > 0 ? 1 : 0) + (other.getX() > 0 ? 1 : 0) +
                (this.getY() > 0 ? 1 : 0) + (other.getY() > 0 ? 1 : 0) +
                (this.getZ() > 0 ? 1 : 0) + (other.getZ() > 0 ? 1 : 0);

        return (double) intersection / union;
    }

    // Default method to calculate Minkowski Distance (generalized distance)
    default double minkowskiDistance(Position other, int p) {
        double dx = Math.pow(Math.abs(this.getX() - other.getX()), p);
        double dy = Math.pow(Math.abs(this.getY() - other.getY()), p);
        double dz = Math.pow(Math.abs(this.getZ() - other.getZ()), p);
        return Math.pow(dx + dy + dz, 1.0 / p);
    }

    // Default method to calculate Hamming Distance (for strings or sequences)
    default int hammingDistance(Position other) {
        int dx = (this.getX() != other.getX()) ? 1 : 0;
        int dy = (this.getY() != other.getY()) ? 1 : 0;
        int dz = (this.getZ() != other.getZ()) ? 1 : 0;
        return dx + dy + dz;
    }

    // Default method to calculate Jaro-Winkler Distance (string similarity)
    default double jaroWinklerDistance(Position other) {
        String str1 = this.getX() + "," + this.getY() + "," + this.getZ();
        String str2 = other.getX() + "," + other.getY() + "," + other.getZ();
        return jaroWinkler(str1, str2);
    }

    // Helper method for Jaro-Winkler calculation (string-based)
    private static double jaroWinkler(String s1, String s2) {
        int len1 = s1.length();
        int len2 = s2.length();

        if (len1 == 0 || len2 == 0) return 0.0;

        int matchDistance = Math.max(len1, len2) / 2 - 1;
        boolean[] s1Matches = new boolean[len1];
        boolean[] s2Matches = new boolean[len2];
        int matches = 0;

        // Count matches
        for (int i = 0; i < len1; i++) {
            int start = Math.max(0, i - matchDistance);
            int end = Math.min(len2 - 1, i + matchDistance);

            for (int j = start; j <= end; j++) {
                if (!s2Matches[j] && s1.charAt(i) == s2.charAt(j)) {
                    s1Matches[i] = true;
                    s2Matches[j] = true;
                    matches++;
                    break;
                }
            }
        }

        if (matches == 0) return 0.0;

        int transpositions = 0;
        int k = 0;
        for (int i = 0; i < len1; i++) {
            if (s1Matches[i]) {
                while (!s2Matches[k]) k++;
                if (s1.charAt(i) != s2.charAt(k)) transpositions++;
                k++;
            }
        }

        double jaro = ((double) matches / len1 + (double) matches / len2 + (double) (matches - transpositions / 2) / matches) / 3.0;
        double jaroWinkler = jaro + 0.1 * Math.min(0.1, 1.0) * (1 - jaro); // Prefix scaling
        return jaroWinkler;
    }

    // Default method to calculate Kolmogorov-Smirnov Distance
    default double ksDistance(Position other, double[] cumulativeDist1, double[] cumulativeDist2) {
        double maxDiff = 0;
        for (int i = 0; i < cumulativeDist1.length; i++) {
            maxDiff = Math.max(maxDiff, Math.abs(cumulativeDist1[i] - cumulativeDist2[i]));
        }
        return maxDiff;
    }

    // Default method to calculate Spearman’s Footrule Distance
    default double spearmanFootruleDistance(Position other) {
        double[] thisRanks = {this.getX(), this.getY(), this.getZ()};
        double[] otherRanks = {other.getX(), other.getY(), other.getZ()};

        // Sort ranks
        Arrays.sort(thisRanks);
        Arrays.sort(otherRanks);

        double distance = 0;
        for (int i = 0; i < thisRanks.length; i++) {
            distance += Math.abs(thisRanks[i] - otherRanks[i]);
        }
        return distance;
    }

    // Default method to calculate L∞ (Chebyshev) Distance
    default double chebyshevDistance(Position other) {
        return Math.max(Math.abs(this.getX() - other.getX()),
                Math.max(Math.abs(this.getY() - other.getY()),
                        Math.abs(this.getZ() - other.getZ())));
    }

    // Default method to calculate Tukey’s Bi-weight Midvariance
    default double tukeyBiweightMidvariance(Position other) {
        double dx = this.getX() - other.getX();
        double dy = this.getY() - other.getY();
        double dz = this.getZ() - other.getZ();
        double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);

        // Tukey's Bi-weighting
        double c = 9.0;  // a common choice for c
        if (distance < c) {
            return Math.pow(distance, 2) / (c * c);
        } else {
            return 0.0;
        }
    }

    // Default method to calculate Lp Norm Distance
    default double lpNormDistance(Position other, int p) {
        double dx = Math.pow(Math.abs(this.getX() - other.getX()), p);
        double dy = Math.pow(Math.abs(this.getY() - other.getY()), p);
        double dz = Math.pow(Math.abs(this.getZ() - other.getZ()), p);
        return Math.pow(dx + dy + dz, 1.0 / p);
    }

    // Default method to calculate Kullback-Leibler Divergence
    default double klDivergence(Position other) {
        double px = this.getX() / (double)(this.getX() + other.getX() + 1e-6);
        double py = this.getY() / (double)(this.getY() + other.getY() + 1e-6);
        double pz = this.getZ() / (double)(this.getZ() + other.getZ() + 1e-6);
        double qx = other.getX() / (double)(this.getX() + other.getX() + 1e-6);
        double qy = other.getY() / (double)(this.getY() + other.getY() + 1e-6);
        double qz = other.getZ() / (double)(this.getZ() + other.getZ() + 1e-6);
        return px * Math.log(px / qx) + py * Math.log(py / qy) + pz * Math.log(pz / qz);
    }

    // Default method to calculate Hellinger Distance
    default double hellingerDistance(Position other) {
        double px = Math.sqrt(this.getX() / (double)(this.getX() + other.getX() + 1e-6));
        double py = Math.sqrt(this.getY() / (double)(this.getY() + other.getY() + 1e-6));
        double pz = Math.sqrt(this.getZ() / (double)(this.getZ() + other.getZ() + 1e-6));
        double qx = Math.sqrt(other.getX() / (double)(this.getX() + other.getX() + 1e-6));
        double qy = Math.sqrt(other.getY() / (double)(this.getY() + other.getY() + 1e-6));
        double qz = Math.sqrt(other.getZ() / (double)(this.getZ() + other.getZ() + 1e-6));
        return 1 / Math.sqrt(2) * Math.sqrt(Math.pow(px - qx, 2) + Math.pow(py - qy, 2) + Math.pow(pz - qz, 2));
    }


    // Default method to calculate Mahalanobis Squared Distance
    default double mahalanobisSquaredDistance(Position other, double[][] covarianceMatrix) {
        double[] diff = {this.getX() - other.getX(), this.getY() - other.getY(), this.getZ() - other.getZ()};
        double[][] invCov = invertMatrix(covarianceMatrix); // Matrix inversion method as earlier
        double squaredDist = 0.0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                squaredDist += diff[i] * invCov[i][j] * diff[j];
            }
        }
        return squaredDist;
    }

    // Default method to calculate Fuzzy Jaccard Index (for fuzzy sets)
    default double fuzzyJaccardIndex(Position other) {
        double dx = Math.abs(this.getX() - other.getX());
        double dy = Math.abs(this.getY() - other.getY());
        double dz = Math.abs(this.getZ() - other.getZ());
        double numerator = dx + dy + dz;
        double denominator = (this.getX() + this.getY() + this.getZ()) + (other.getX() + other.getY() + other.getZ());
        return numerator / denominator;
    }

    // Default method to calculate L2 Norm Distance (Euclidean Distance)
    default double l2NormDistance(Position other) {
        return euclideanDistance(other); // This is the same as Euclidean Distance
    }

    // Default method to calculate Ratio Distance
    default double ratioDistance(Position other) {
        double dx = this.getX() / (double)(other.getX() + 1e-6);
        double dy = this.getY() / (double)(other.getY() + 1e-6);
        double dz = this.getZ() / (double)(other.getZ() + 1e-6);
        return Math.abs(dx - 1) + Math.abs(dy - 1) + Math.abs(dz - 1);
    }

    // Default method to calculate Ruzicka Distance
    default double ruzickaDistance(Position other) {
        double dx = Math.pow(Math.abs(this.getX() - other.getX()), 4);
        double dy = Math.pow(Math.abs(this.getY() - other.getY()), 4);
        double dz = Math.pow(Math.abs(this.getZ() - other.getZ()), 4);
        return Math.pow(dx + dy + dz, 1.0 / 4);
    }

    // Default method to calculate Tanimoto Similarity (Jaccard Similarity)
    default double tanimotoSimilarity(Position other) {
        double intersection = Math.min(this.getX(), other.getX()) + Math.min(this.getY(), other.getY()) + Math.min(this.getZ(), other.getZ());
        double union = this.getX() + this.getY() + this.getZ() + other.getX() + other.getY() + other.getZ() - intersection;
        return intersection / union;
    }

    // Helper method to invert a 3x3 matrix
    static double[][] invertMatrix(double[][] matrix) {
        double[][] inverse = new double[3][3];

        // Calculate the determinant of the matrix
        double determinant = matrix[0][0] * (matrix[1][1] * matrix[2][2] - matrix[1][2] * matrix[2][1])
                - matrix[0][1] * (matrix[1][0] * matrix[2][2] - matrix[1][2] * matrix[2][0])
                + matrix[0][2] * (matrix[1][0] * matrix[2][1] - matrix[1][1] * matrix[2][0]);

        // Check if the determinant is zero (the matrix is singular and cannot be inverted)
        if (determinant == 0) {
            throw new IllegalArgumentException("Matrix is singular and cannot be inverted.");
        }

        // Calculate the inverse of the matrix using the adjugate formula
        double invDet = 1.0 / determinant;

        // Calculate the adjugate matrix (cofactors of each element)
        inverse[0][0] = invDet * (matrix[1][1] * matrix[2][2] - matrix[1][2] * matrix[2][1]);
        inverse[0][1] = invDet * (matrix[0][2] * matrix[2][1] - matrix[0][1] * matrix[2][2]);
        inverse[0][2] = invDet * (matrix[0][1] * matrix[1][2] - matrix[0][2] * matrix[1][1]);

        inverse[1][0] = invDet * (matrix[1][2] * matrix[2][0] - matrix[1][0] * matrix[2][2]);
        inverse[1][1] = invDet * (matrix[0][0] * matrix[2][2] - matrix[0][2] * matrix[2][0]);
        inverse[1][2] = invDet * (matrix[0][2] * matrix[1][0] - matrix[0][0] * matrix[1][2]);

        inverse[2][0] = invDet * (matrix[1][0] * matrix[2][1] - matrix[1][1] * matrix[2][0]);
        inverse[2][1] = invDet * (matrix[0][1] * matrix[2][0] - matrix[0][0] * matrix[2][1]);
        inverse[2][2] = invDet * (matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0]);

        return inverse;
    }

    static int smallestEncompassingPowerOfTwo(int value) {
        int i = value - 1;
        i |= i >> 1;
        i |= i >> 2;
        i |= i >> 4;
        i |= i >> 8;
        i |= i >> 16;
        return i + 1;
    }
}
