/*
 * (C) Copyright 2014 Amaury Crickx
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package bases;

import java.util.HashMap;
import java.util.Map;

/**
 * Hann Window function
 * <p>
 * Threading : this class is thread safe
 * </p>
 * @see <a href="http://en.wikipedia.org/wiki/Hann_function">Hann function<a/>
 * @see WindowFunction
 * @author Amaury Crickx
 */
public final class HannWindowFunction 
        extends WindowFunction {
    
    private static final Map<Integer, double[]> factorsByWindowSize = new HashMap<Integer, double[]>();

    /**
     * Constructor imposed by WindowFunction
     * @param windowSize the windowSize
     * @see WindowFunction#WindowFunction(int)
     */
    public HannWindowFunction(int windowSize) {
        super(windowSize);
    }

    @Override
    protected double[] getPrecomputedFactors(int windowSize) {
        // precompute factors for given window, avoid re-calculating for several instances
        synchronized (HannWindowFunction.class) {
            double[] factors;
            if(factorsByWindowSize.containsKey(windowSize)) {
                factors = factorsByWindowSize.get(windowSize);
            } else {
                factors = new double[windowSize];
                int sizeMinusOne = windowSize - 1;
                for(int i = 0; i < windowSize; i++) {
                    factors[i] = 0.5d * (1 - Math.cos((TWO_PI * i) / sizeMinusOne));
                }
                factorsByWindowSize.put(windowSize, factors);
            }
            return factors;
        }
    }

}
