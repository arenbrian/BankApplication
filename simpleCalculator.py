
def main():
    userInput = input("Please enter two numbers with operator in between: ")
    input_values = userInput.split(" ")

    if len(input_values) != 3:
        print("Invalid input. Please enter two numbers with operator in between.")
        return

    operand1, operator, operand2 = input_values

    operand1 = int(operand1)
    operand2 = int(operand2)

    if operator == "+":
        result = addition(operand1, operand2)
    elif operator == "-":
        result = subtraction(operand1, operand2)
    elif operator == "*":
        result = multiplication(operand1, operand2)
    elif operator == "//":
        result = division(operand1, operand2)
    else:
        result = "Invalid operator"

    print("Result:", result)

def addition(a,b):
    return a + b
def subtraction(a,b):
    return a - b
def multiplication(a,b):
    return a * b
def division(a,b):
    return a // b

main()