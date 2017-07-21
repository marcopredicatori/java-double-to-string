/**
 * MIT License
 *
 * Copyright (c) 2017 Marco Predicatori (marco@predicatori.it)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

class DoubleToString {

    /**
     * Formats a number relying only on the given separators and desired precision.
     *
     * @param n        The number to be formatted.
     * @param prec     The desired number of decimal digits.
     * @param thousSep The thousands separator, can be either dot or comma.
     * @param decSep   The decimal separator, can be either dot or comma, but can't be the same as
     *                 the thousands separator.
     * @return A string containing the number formatted accordingly to the input parameters.
     */
    public static String FormatNumber(Double n, int prec, String thousSep, String decSep) {

        /**
         * Check the separators
         */

        if (thousSep != "." && thousSep != ",") {
            return "Invalid thousands separator";
        }

        if (decSep != "." && decSep != ",") {
            return "Invalid decimal separator";
        }

        if (decSep == thousSep) {
            return "The thousands separator cannot be the same as the decimal separator";
        }

        String string = "";

        // Check the sign, and store it.
        String sign = "";
        if (n < 0) {
            sign = "-";
            // Deal only with positive values.
            n = -n;
        }

        // Extract the decimal portion.
        String decString = "";
        String decPart = "";
        if ((n - n.intValue()) > 0 &&
                prec > 0) {
            decString = (String.format("%f", n)).substring(String.format("%f", n).indexOf(","));
            decPart = decSep + decString.substring(1, Math.min(prec + 1, decString.length()));
        } else {
            decPart = "";
        }

        // Count the triplets of the integer portion.
        int tripCount = ((Double) (Math.log10(n) / 3)).intValue() + 1;

        // Separate the triplets
        String triplet = "";
        Boolean isNotFirstTriplet = false;
        while (tripCount > 0) {
            tripCount--;
            triplet = Integer.toString(((Double) (n / (Math.pow(1000, tripCount)))).intValue());

            if (isNotFirstTriplet) {
                // Left pad with zeroes the triplets shorter than 3 digits
                while (triplet.length() < 3) {
                    triplet = "0" + triplet;
                }
            }
            isNotFirstTriplet = true;

            string = string + triplet;
            if (tripCount > 0) {
                string = string + thousSep;
            }
            n = n - ((((Double) (n / (Math.pow(1000, tripCount)))).intValue()) * Math.pow(1000, tripCount));
        }

        // In case of negative precision, the last -prec digits are replaced with zeroes
        if (prec < 0) {

            int positionsToBeZeroed = (-prec) + (((-prec) - 1) / 3);
            int curPos = string.length() - 1;
            StringBuilder sx = new StringBuilder(string);

            while (curPos >= (string.length() - positionsToBeZeroed) &&
                    curPos >= 0
                    ) {
                if (!sx.substring(curPos, curPos + 1).equalsIgnoreCase(".")) {
                    sx.replace(curPos, curPos + 1, "0");
                }
                curPos--;
            }
            string = sx.toString();
        }

        string = sign + string + decPart;

        return string;
    }

    /**
     * Formats a number with a dot as thousands separator, a comma as decimal separator and two
     * decimal digits.
     *
     * @param n The number to be formatted.
     * @return A string containing the formatted number.
     */
    public static String FormatNumber(Double n) {

        /**
         * Set the default separators and precision
         */
        int prec = 2;
        String thousSep = ".";
        String decSep = ",";

        String string = "";

        // Check the sign, and store it.
        String sign = "";
        if (n < 0) {
            sign = "-";
            // Deal only with positive values.
            n = -n;
        }

        // Extract the decimal portion.
        String decString = "";
        String decPart = "";
        if ((n - n.intValue()) > 0 &&
                prec > 0) {
            decString = (String.format("%f", n)).substring(String.format("%f", n).indexOf(","));
            decPart = decSep + decString.substring(1, Math.min(prec + 1, decString.length()));
        } else {
            decPart = "";
        }

        // Count the triplets of the integer portion.
        int tripCount = ((Double) (Math.log10(n) / 3)).intValue() + 1;

        // Separate the triplets
        String triplet = "";
        Boolean isNotFirstTriplet = false;
        while (tripCount > 0) {
            tripCount--;
            triplet = Integer.toString(((Double) (n / (Math.pow(1000, tripCount)))).intValue());

            if (isNotFirstTriplet) {
                // Left pad with zeroes the triplets shorter than 3 digits
                while (triplet.length() < 3) {
                    triplet = "0" + triplet;
                }
            }
            isNotFirstTriplet = true;

            string = string + triplet;
            if (tripCount > 0) {
                string = string + thousSep;
            }
            n = n - ((((Double) (n / (Math.pow(1000, tripCount)))).intValue()) * Math.pow(1000, tripCount));
        }

        string = sign + string + decPart;

        return string;
    }
}
