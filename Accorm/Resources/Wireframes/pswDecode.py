def decode_caesar_cipher(ciphertext, key):
    """
    Decodes a Caesar cipher using a numeric key.

    :param ciphertext: The encrypted text
    :param key: The numeric key used for decoding
    :return: Decoded plaintext
    """
    plaintext = ""
    key_digits = [int(digit) for digit in str(key)]  # Extract digits from the key
    key_length = len(key_digits)

    for i, char in enumerate(ciphertext):
        if char.isalpha():
            # Determine if the character is uppercase or lowercase
            ascii_offset = ord('A') if char.isupper() else ord('a')

            # Use the corresponding digit of the key for shifting
            shift = key_digits[i % key_length]
            decoded_char = chr((ord(char) - ascii_offset - shift) % 26 + ascii_offset)
            plaintext += decoded_char
        elif char.isdigit():
            # For digits, subtract the corresponding key digit and wrap around 0-9
            shift = key_digits[i % key_length]
            decoded_char = chr((ord(char) - ord('0') - shift) % 10 + ord('0'))
            plaintext += decoded_char
        else:
            # Non-alphabetic and non-numeric characters remain the same
            plaintext += char

    return plaintext





if __name__ == "__main__":
    # Example usage
    encrypted_text = input("Enter the encrypted text: ")
    print("\n--- Decode with a known numeric key ---")
    key = int(input("Enter the numeric key: "))
    decoded_text = decode_caesar_cipher(encrypted_text, key)
    print(f"Decoded text: {decoded_text}")
