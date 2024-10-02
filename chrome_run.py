import os
import subprocess
import sys


def open_chrome_with_user_data():
    # Check the operating system
    if os.name == 'nt':  # Windows
        # Paths for Chrome in regular and x86 (32-bit) directories
        chrome_path = r'C:\Program Files\Google\Chrome\Application\chrome.exe'
        chrome_path_x86 = r'C:\Program Files (x86)\Google\Chrome\Application\chrome.exe'

        # Check if Chrome is in the regular directory
        if os.path.isfile(chrome_path):
            subprocess.run([chrome_path, "--remote-debugging-port=9222"])
        # Check if Chrome is in the x86 directory
        elif os.path.isfile(chrome_path_x86):
            subprocess.run([chrome_path_x86, "--remote-debugging-port=9222"])
        else:
            print("Chrome executable not found in standard locations.")
            sys.exit(1)
    elif sys.platform == "darwin":  # Macos
        subprocess.run(["/Applications/Google Chrome.app/Contents/MacOS/Google Chrome", "--remote-debugging-port=9222"])
    else:
        subprocess.run(['google-chrome', "--remote-debugging-port=9222"])

    # Close the script
    sys.exit()


if __name__ == "__main__":
    open_chrome_with_user_data()
