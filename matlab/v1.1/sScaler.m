function [Rbez,ys] = sScaler (yz,ys)
%ermittelt den Bereich in dem sich die S-Parameter bewegen und passt diesen
%an, dass er eine mindest Bandbreite von 0.5 hat
%w=frequenzenliste
%ys=S-parameter Messwerte
%R0=alter Bezugswiederstand
%Rbez=neuer Bezugswiederstand
R0 = 50;
Rbez = 0.00001;
yz = s2z(R0,ys);
ys = z2s(Rbez,yz);

while ((abs(abs(max(ys))-abs(min(ys))))<0.6)&(Rbez<10000)
    yz = s2z(Rbez,ys);
    Rbez = Rbez*2;
    ys = z2s(Rbez,yz); 
end



