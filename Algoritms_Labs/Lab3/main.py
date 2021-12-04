from PIL import Image, ImageDraw


def comparing(point_1, point_2):
    if point_1[0] < point_2[0]: return False
    elif (point_1[0] == point_2[0]) and (point_1[1] < point_2[1]): return False
    else: return True


def sorting(points_lst):
    p = 0
    while True:
        for i in range(0, len(points_lst) - 1):
            if comparing(points_lst[i], points_lst[i + 1]):
                points_lst[i], points_lst[i + 1] = points_lst[i + 1], points_lst[i]
                p += 1
        if p == 0: break
        else: p = 0


def hull(sorted_points):
    upper = []
    lower = []
    for i in range(1, len(sorted_points) - 1):
        if det(sorted_points[0], sorted_points[len(sorted_points) - 1], sorted_points[i]) >= 0:
            upper.append(sorted_points[i])
        else:
            lower.append(sorted_points[i])
    return [sorted_points[0]] + build(sorted_points[0], sorted_points[len(sorted_points) - 1], upper) \
           + [sorted_points[len(sorted_points) - 1]] + build(sorted_points[len(sorted_points) - 1], sorted_points[0],lower)


def build(point_1, point_2, points):
    if len(points) != 0:
        lst_1 = []
        lst_2 = []
        max_len = 0
        max_area = 0
        for i in range(0, len(points)):
            cur_area = det(point_1, point_2, points[i])
            if cur_area > max_area:
                max_area = cur_area
                max_len = i
        max_len_point = points[max_len]
        del points[max_len]
        for point in points:
            if det(point_1, max_len_point, point) >= 0:
                lst_1.append(point)
            elif det(max_len_point, point_2, point) >= 0:
                lst_2.append(point)
        return build(point_1, max_len_point, lst_1) + [max_len_point] \
               + build(max_len_point, point_2, lst_2)
    else:
        return []


def det(point_1, point_2, point_3):
    return point_1[0] * point_2[1] + point_3[0] * point_1[1] + point_2[0] * point_3[1] - point_3[0] * point_2[1] \
           - point_2[0] * point_1[1] - point_1[0] * point_3[1]


HEIGHT = 960
WIDTH = 540
points_list = []

file_name = "DS2.txt"

image = Image.new("RGB", (WIDTH, HEIGHT))
draw = ImageDraw.Draw(image)

hull_image = Image.new("RGB", (WIDTH, HEIGHT))
hull_draw = ImageDraw.Draw(hull_image)

file = open(file_name, 'r')

for line in file:
    line = line.split(" ")
    draw.point((int(line[0]), int(line[1])))
    points_list.append([int(line[0]), int(line[1])])

sorting(points_list)
hull_list = hull(points_list)

for i in range(0, len(hull_list) - 1):
    hull_draw.line(((hull_list[i][0], hull_list[i][1]), (hull_list[i+1][0], hull_list[i+1][1])), fill="blue")

    draw.line(((hull_list[i][0], hull_list[i][1]), (hull_list[i + 1][0], hull_list[i + 1][1])), fill="blue")

hull_draw.line(((hull_list[-1][0], hull_list[-1][1]), (hull_list[0][0], hull_list[0][1])), fill="blue")
draw.line(((hull_list[-1][0], hull_list[-1][1]), (hull_list[0][0], hull_list[0][1])), fill="blue")

file.close()

hull_file = open("Hull_points.txt", 'w')

for i in hull_list:
    hull_file.write(str(i[0]) + ' ' + str(i[1]))
    hull_file.write("\n")

hull_file.close()
image.save("DS2.png", "PNG")
hull_image.save("DS2_hull.png", "PNG")