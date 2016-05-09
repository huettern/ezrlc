function [s] = s(n,w,a)
%n=Nummer des Ersatzschaltbildes
%w=Frequenzen
%a=parameterliste

r0=a(1);
w0=a(2)*2*pi;
alpha=a(3);
r1=a(4);
l=a(5);
c0=a(6);
c1=a(7);
z0=50;  %Bezugswiderstand

%Neue lösung mit switch
% pn=eval(Pn(n,:));    %polynome des n. Ersatzschaltbildes numerisch berechnen
% pz=eval(Pz(n,:));

switch n
    case 1
        Pz=[0,0,l,r0];
        Pn=[0,0,0,1];
    case 2
        Pz=[0,0,l*r0,0];
        Pn=[0,0,l,r0];
    case 3
        Pz=[0,0,c0*r0,1];
        Pn=[0,0,c0,0];
    case 4
        Pz=[0,0,0,r0];
        Pn=[0,0,c0*r0,1];
    case 5
        Pz=[0,l*c0,r0*c0,1];
        Pn=[0,0,c0,0];
    case 6
        Pz=[0,0,r0*l,0];
        Pn=[0,c0*l*r0,l,r0];
    case 7
        Pz=[0,0,l,r0];
        Pn=[0,c0*l,c0*r0,1];
    case 8
        Pz=[0,c0*l*r0,l,r0];
        Pn=[0,0,c0*r0,1];
    case 9
        Pz=[0,0,c0*r0*r1,r0+r1];
        Pn=[0,0,c0*r0,1];
    case 10
        Pz=[0,l*c0*r0,l+c0*r0*r1,r0+r1];
        Pn=[0,0,c0*r0,1];
    case 11
        Pz=[0,c0*l*r0*r1,l*r0+l*r1,r1*r0];
        Pn=[0,c0*l*r0,l,r0];
    case 12
        Pz=[0,0,l,r1];
        Pn=[0,c0*l,c0*r1+l*r0,r1*r0+1];
    case 13
        Pz=[0,l*c0,r0*c0,1];
        Pn=[c0*c1*l,c0*c1*r0,c0+c1,0];
    case 14
        Pz=[0,l*c0,c0*(r0*(1+(w/w0 ).^alpha)),1];
        Pn=[0,0,c0,0];
    case 15
        Pz=[0,0,l,r0*(1+(w/w0 ).^alpha)];
        Pn=[0,c0*l,c0*(r0*(1+(w/w0 ).^alpha)),1];
    case 16
        Pz=[0,c0*l*(r0*(1+(w/w0 ).^alpha)),l,r0*(1+(w/w0 ).^alpha)];
        Pn=[0,0,c0*(r0*(1+(w/w0 ).^alpha)),1];
    case 17
        Pz=[0,c0*l*(r0*(1+(w/w0 ).^alpha)),l,r0*(1+(w/w0 ).^alpha)];
        Pn=[0,0,c0*r0,1];
    case 18
        Pz=[0,l*c0*r0,l+c0*r0*(r1*(1+(w/w0 ).^alpha)),r0+(r1*(1+(w/w0 ).^alpha))];
        Pn=[0,0,c0*r0,1];
    case 19
        Pz=[0,c0*l*r0*(r1*(1+(w/w0 ).^alpha)),l*r0+l*(r1*(1+(w/w0 ).^alpha)),(r1*(1+(w/w0 ).^alpha))*r0];
        Pn=[0,c0*l*r0,l,r0];
    case 20
        Pz=[0,0,l,r1*(1+(w/w0 ).^alpha)];
        Pn=[0,c0*l,c0*(r1*(1+(w/w0 ).^alpha))+l*r0,(r1*(1+(w/w0 ).^alpha))*r0+1];
    case 21
        Pz=[0,l*c0,c0*(r0*(1+(w/w0 ).^alpha)),1];
        Pn=[c0*c1*l,c0*c1*(r0*(1+(w/w0 ).^alpha)),c0+c1,0];
end

         
z=polyval(Pz,1j.*w)./polyval(Pn,1j.*w); %z-parameter berechnen
s=(z-z0)./(z+z0);                      %s-parameter berechnen
end
