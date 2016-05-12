function [ES,Parameter1Platz3]= ESextend(n,Parameter1Platz2,w,ys,yz)
%Erweitert ES mit zwei Elementen auf 3 Elemente
%Parameter1Platz2 = Parameter des 1.Platz mit 2 Elementen
%Parameter1Platz3 = Parameter des 1.Platz mit 3 Elementen

%n=Ersatzschaltbild mit 2 Elementen
%w=Frequenzenarray
%ys=s-Parameter
[x,fehlerarray]=fehlersummeZAbs(n,w,yz,Parameter1Platz2);
[x,wDeltaMaxindex]=max(fehlerarray); %w mit der grössten abweichung in Zabs
clear x
wDeltaMax=w(wDeltaMaxindex);
z=yz(wDeltaMaxindex);


switch n
    case 1
        ES=7;
        r0=Parameter1Platz2(1);
        l=Parameter1Platz2(5);
        %c0=(1/z-1/(1j*wDeltaMax*l+r0))/(1j*wDeltaMax);
        c0=(imag(1/z)+wDeltaMax*l/(r0^2+(wDeltaMax*l)^2))/wDeltaMax;
        Parameter1Platz3=Parameter1Platz2;
        Parameter1Platz3(6)=c0;   
    case 2
        ES=6;
        r0=Parameter1Platz2(1);
        l=Parameter1Platz2(5);
        %c0=(1/z-1/r0-1/(1j*wDeltaMax*l))/(1j*wDeltaMax);
        c0=(imag(1/z)+1/(wDeltaMax*l))/wDeltaMax;
        Parameter1Platz3=Parameter1Platz2;
        Parameter1Platz3(6)=c0; 
    case 3
        ES=5;
        r0=Parameter1Platz2(1);
        c0=Parameter1Platz2(6);
        %l=(z-r0-1/(1j*wDeltaMax*c0))/(1j*wDeltaMax);
        l=(imag(z)+1/(wDeltaMax*c0))/wDeltaMax;
        Parameter1Platz3=Parameter1Platz2;
        Parameter1Platz3(5)=l; 
    case 4
        ES=8;
        r0=Parameter1Platz2(1);
        c0=Parameter1Platz2(6);
        %l=(z-1/(1j*wDeltaMax*c0+1/r0))/(1j*wDeltaMax);
        l=(imag(z)+wDeltaMax*c0/(1/r0^2+(wDeltaMax*c0)^2))/wDeltaMax;
        Parameter1Platz3=Parameter1Platz2;
        Parameter1Platz3(5)=l; 
end
