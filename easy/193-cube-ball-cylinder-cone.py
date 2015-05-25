"""
[2014-12-15] Challenge #193 [Easy] A Cube, Ball, Cylinder, Cone walk into a warehouse
https://www.reddit.com/r/dailyprogrammer/comments/2peac9/20141215_challenge_193_easy_a_cube_ball_cylinder/

Given a volume, find the packaging dimensions (that minimizes surface area) for a cube, a sphere, 
a cylinder, and a cone	
"""
from math import pi
def devolumify(vol):
	#cube
	cubeSide = vol ** (1/3.0)
	cubeString = "Cube: {0} width, {0} high, {0} tall".format(cubeSide)

	#cylinder; minimum surface area attained when height = diameter
	cylR = (vol / (2*pi)) ** (1/3.0)
	cylString = "Cylinder: {0} radius, {1} height".format(cylR, cylR*2)

	#sphere
	sphereR = (vol * 0.75 * pi) ** (1/3.0)
	sphereString = "Sphere: {0} radius".format(sphereR)

	#cone;
	coneR = (3*vol / (pi * 2 ** (1/2.0)) ) ** (1/3.0) #radius of R that minimizes SA
	coneH = 3*vol / (pi * coneR**2)
	coneString = "Cone: {0} radius, {1} height".format(coneR, coneH)

	return cubeString, cylString, sphereString, coneString
