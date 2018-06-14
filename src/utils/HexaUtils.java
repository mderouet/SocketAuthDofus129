package utils;

public class HexaUtils {
	
	// Check whether string is hexadecimal or not

	public static boolean isHexadecimal(String value) {
        boolean ret;
        try {
            int t = Integer.parseInt(value, 16);
            ret = true;
        } catch (NumberFormatException e) {
            ret = false;
        }
        return (ret);
    }
    
	// Convert string to hexa
	public static String hexToAscii(String hexStr) {
		StringBuilder output = new StringBuilder("");

		for (int i = 0; i < hexStr.length(); i += 2) {
			String str = hexStr.substring(i, i + 2);
			output.append((char) Integer.parseInt(str, 16));
		}

		return output.toString();
	}
	
}
