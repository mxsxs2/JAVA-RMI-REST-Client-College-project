import requests
import json
import sys
import os
import argparse


# Add description
parser = argparse.ArgumentParser(
    description='Car hire admin client')
# Add command line arguments
parser.add_argument('--resturl',
                    help='The url to connect to. Default is http://localhost:8080/REST-Server/')
# Parse the arguments
args = parser.parse_args()
# Set endpoint
if args.resturl != None:
    endpoint = args.resturl
else:
    endpoint = "http://localhost:8080/REST-Server/"

# Set content-type and accept to json
headers = {'content-type': 'application/json', 'accept': 'application/json'}


class AdminService:
    """
    Class to interact with the admin service
    """
    global endpoint
    global headers

    def __init__(self):
        # Set endpoint
        self.endpoint = endpoint+"admin/"

    def getBookingList(self):
        # Do request
        r = requests.get(self.endpoint+'booking/list', headers=headers)
        if r.status_code == 200:
            # Convert to json
            return r.json()
        return []

    def addCar(self, car):
        # Do request
        r = requests.put(self.endpoint+'add/car',
                         headers=headers, data=json.dumps(car))
        if r.status_code == 200 or r.status_code == 204:
            return True
        return False


class Menu:
    """
    Class to show and interact with the menu.
    """

    def __init__(self):
        # Clear the console
        os.system('clear')
        # Initialise admin service
        self.adminservice = AdminService()
        # Show the menu
        self.mainMenu()

    def mainMenu(self):
        item = "-1"
        while item != "0":
            print("\nPlease choose the menu you want to start:")
            print("1. Get booking list")
            print("2. Add new car")
            print("0. Exit")
            item = input(" >>  ")
            # If the user choose
            if item == "1":
                self.getBookingList()
            if item == "2":
                self.addCar()

    def getBookingList(self):
        """
        Prints out the list of bookings
        """
        data = self.adminservice.getBookingList()
        if len(data) > 0:
            print("\nBookings:")
            for d in data:
                print("\n")
                for k, v in d.items():
                    print("%s : %s" % (k, v))
        else:
            print("\n No bookings.")

    def addCar(self):
        """
        Menu for adding a car
        """
        def getInput(desc, minLength=0, maxLength=100):
            """
            Input validator
            """
            while 1 == 1:
                print(desc+":")
                i = input(" >>  ")
                if len(i) > minLength and len(i) < maxLength:
                    return i

        car = {
            "id": getInput(desc="Car reg", minLength=7, maxLength=10),
            "model": getInput("Model"),
            "make": getInput("Make"),
            "color": getInput("Color")
        }

        if self.adminservice.addCar(car) == True:
            print("The car is added")
        else:
            print("The could not be added try again later")


try:
    # Try to connect to the endpoint
    requests.get(endpoint, headers=headers)
    # Start the menu
    Menu()
except Exception:
    print("The url provided is not available.")
