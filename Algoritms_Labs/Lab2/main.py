from PIL import Image, ImageDraw

HEIGHT = 960
WIDTH = 540

file_name = "DS2.txt"

image = Image.new("RGB", (WIDTH, HEIGHT))
draw = ImageDraw.Draw(image)

file = open(file_name, 'r')

for line in file:
    line = line.split(" ")
    draw.point((int(line[0]),int(line[1])))

file.close()
image.save("DS2.png", "PNG")