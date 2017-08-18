
#include <string.h>
#include <stdlib.h>
#include <stdio.h>
#include <time.h>
#include <string.h>
#include <math.h>
#include <cmath>
//#include "stdafx.h"
#include "MCFSimplex.h"


int main(int arg, char ** argv)
{
	double dfct[4] = {-2,0,0,2};
	unsigned int start[4] = {1,2,3,1};
	unsigned int end[4] = {2,3,4,4};
	double ub[4] = {2,2,3,1};
	double cost[4] = {3,1,1,3};
	
	MCFSimplex * mcf = new MCFSimplex();
	mcf->LoadNet(4,4,4,4,ub,cost,dfct,start,end);
	mcf->SolveMCF();
	std::cout << "Status:" << mcf->MCFGetStatus() << endl;
	MCFClass::FRow F= new double[4];
	MCFClass::Index_Set nms = new unsigned int[4];
	
	mcf->MCFGetX( F,  nms, 0, MCFClass::Inf<MCFClass::Index>());

	for (int i = 0; i < 4; i++)
		cout <<nms[i]<<"|"<< F[i] << endl;

	mcf->CloseArc(0);
	mcf->SolveMCF();
	std::cout << "Status:" << mcf->MCFGetStatus() << endl;
	
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