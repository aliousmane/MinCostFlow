
#include <string.h>
#include <stdlib.h>
#include <stdio.h>
#include <time.h>
#include <string.h>
#include <math.h>
#include <cmath>
#include <fstream>      // std::filebuf
#include <iostream>
//#include "stdafx.h"
#include "MCFSimplex.h"


int main(int arg, char ** argv)
{
	////double dfct[4] = {-2,0,0,2};
	//double dfct[6] = { -10, 0, 0,0,0, 10 };
	////unsigned int start[4] = {1,2,3,1};
	//unsigned int start[8] = { 1,1,2,3,5,5,4,4 };
	////unsigned int end[4] = {2,3,4,4};
	//unsigned int end[8] = { 2, 3,3,5,4,6,2,6 };
	////double ub[4] = {2,2,3,1};
	//double ub[8] = { 4,8,5,10,8,8,8,8 };
	////double cost[4] = {3,1,1,3};
	//double cost[8] = { 1,5,0,1,0,9,1,1};

	MCFSimplex * mcf = new MCFSimplex();
	//mcf->LoadNet(6,8,6,8,ub,cost,dfct,start,end);
	//mcf->SolveMCF();
	//std::cout << "Status:" << mcf->MCFGetStatus() << endl;
	MCFClass::FRow F = new double[4];
	MCFClass::Index_Set nms = new unsigned int[4];
	std::filebuf fb;
	if (fb.open("C:\\Users\\A.K.J\\Documents\\Visual Studio 2013\\Projects\\minflow\\Input1.csv", std::ios::in))
	{
		std::istream is(&fb);
		
		mcf->LoadDMX(is);
		std::cout << "Status:" << mcf->MCFGetStatus() << endl;
		mcf->SolveMCF();
		std::cout << "Status:" << mcf->MCFGetStatus() << endl;
		mcf->MCFGetX(F, NULL, 0, MCFClass::Inf<MCFClass::Index>());
		
		cout << mcf->MCFGetFO() << endl;
		for (int i = 0; i < 4; i++)
			cout << nms[i] << "|" << F[i] << endl;

		mcf->CloseArc(0);
		//mcf->SolveMCF();
		std::cout << "Status:" << mcf->MCFGetStatus() << endl;
		fb.close();
	}
		
	
	
	system("PAUSE");
	/*MCFSimplex * mcf = new MCFSimplex(4,4);
	mcf->ChgDfct(1,3);
	mcf->ChgDfct(2,0);
	mcf->ChgDfct(3,0);
	mcf->ChgDfct(4,-3);

	
	mcf->ChangeArc(0,1,2);
	mcf->ChgUCap(0,2);
	mcf->ChgCost(0,3);
	
	mcf->ChangeArc(1,2,3);
	mcf->ChgUCap(1,2);
	mcf->ChgCost(1,1);
	
	mcf->ChangeArc(2,3,4);
	mcf->ChgUCap(2,1);
	mcf->ChgCost(2,3);
	
	mcf->ChangeArc(3,1,4);
	mcf->ChgUCap(3,2);
	mcf->ChgCost(3,3);
	
	mcf->SetAlg(1,0);
	
	mcf->SolveMCF();*/
	
	return 0;	
}