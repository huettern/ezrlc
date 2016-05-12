%******************************************************************************
% Project     : AnalyticSolver
% Autor       : Elias Lukas Rotzler
% Filename    : AnalyticSolver.m
% Startdate   : 06.04.16
% Enddate     : 06.04.16
% Version     : 1.0
%******************************************************************************

function [A] = AnalyticSolver(win,y,ys)

%clc; clear all; close all hidden;

%[win,y,ys]=filereader;
y = transpose(y);
% win = [0 2e6 3e6 4e6 5e7 6e6 7e6 8e6 9e6 1e8]; % Platzhalter                           % w-Vektor
% y = [100 100+12.566371i 100+18.849556i 100+25.132741i 100+314.15927i 100+37.699112i 100+43.982297i 100+50.265482i 100+56.548668i 100+238.76104i]; % Platzhalter                             % Z(w) Impedanz anhängig von w

% Frequenz- und Z-Werte bei 2 Unbekannten Bauteilen/Grössen
w21 = win(1,2);                                             % w-Werte für 2 Bauteile
w22 = win(1,end);
Z21 = y(1,2);                                               % zu w-Werten zugehörige Z-Werte
Z22 = y(1,end);

% Frequenz- und Z-Werte bei 3 Unbekannten Bauteilen/Grössen
% n = 3;                                                      % n=(Anzahl unbekannter Bauteile)
% xm = size(win);
% x = [floor(xm(1,2)/n)];                                     % ermittelt schrittbreite für das abtasten der eingangs Werte Z(w)                                                % Schrittbreite
% w31 = win(1,1*x);                                            % w-Werte an den stellen 1, 1*Schrittbreite, usw.
% w32 = win(1,2*x);            
% w33 = win(1,end);
% Z31 = y(1,1*x);                                               % Impedanzen Z(w) bei den ermitelten Werten w1-w3
% Z32 = y(1,2*x);
% Z33 = y(1,end);

diffSabs = diff(abs(ys));
[diffmin,w31index]=max(diffSabs);
[diffmax,w32index]=min(diffSabs);
diffSabs = abs(diffSabs);
[diff0,w33index]=min(diffSabs);
clear diffmin diffmax diff0;
if w32index==w33index
    w33index=ceil(abs(w31index-w32index)/2)+min([w31index,w32index]);
end
if w31index==w33index
    w33index=ceil(abs(w31index-w32index)/2)+min([w31index,w32index]);
end

w31=win(w31index);
w32=win(w32index);
w33=win(w33index);
Z31=y(w31index);
Z32=y(w32index);
Z33=y(w33index);



% Frequenz- und Z-Werte bei 4 Unbekannten Bauteilen/Grössen
n = 4;                                                      % n=(Anzahl unbekannter Bauteile)
xm = [floor(size(win)/n)];                                  % ermittelt schrittbreite für das abtasten der eingangs Werte Z(w)
x = xm(1,2);                                                % Schrittbreite
w41 = win(1,1*x);                                             % w-Werte an den stellen 1, 1*Schrittbreite, usw.
w42 = win(1,2*x);                                                  
w43 = win(1,3*x);
w44 = win(1,end);
Z41 = y(1,1*x);                                               % Impedanzen Z(w) bei den ermitelten Werten w1-w4
Z42 = y(1,2*x);
Z43 = y(1,3*x);
Z44 = y(1,end);

% %------------------------------------------------------------------------------
% % Ersatzschaltbild 1:
% % Anzahl unbekannter Elemente: 2
syms R0 L0
S1 = solve(real(Z31)==R0, imag(Z31)==w31*L0);
R01 = double(S1.R0);
L01 = double(S1.L0);
R01 = real(R01);
L01 = real(L01);

if isempty(R01)==1
    R01=0;
end
if isempty(L01)==1
    L01=0;
end

% s = tf('s');
% H1 = s*L01+R01;
% figure(1)
% subplot(121)
% bode(H1)
% subplot(122)
% nyquist(H1);
% %------------------------------------------------------------------------------

% %------------------------------------------------------------------------------
% % Ersatzschaltbild 2:
% % Anzahl unbekannter Elemente: 2
syms R0 L0
S2 = solve (real(Z31)==(1/R0)/(1/(w31*L0)^2+(1/(R0^2))),imag(Z31)==1/(w31*L0)/(1/(w31*L0)^2+(1/(R0^2))));
R02 = double(S2.R0);
L02 = double(S2.L0);
R02 = real(R02);
L02 = real(L02);

if isempty(R02)==1
    R02=0;
end
if isempty(L02)==1
    L02=0;
end
% s = tf('s');
% H2 = 1/(1/(s*L02)+1/R02);
% figure(2)
% subplot(121)
% bode(H2)
% subplot(122)
% nyquist(H2);
% %------------------------------------------------------------------------------

% %------------------------------------------------------------------------------
% % Ersatzschaltbild 3:
% % Anzahl unbekannter Elemente: 2
syms R0 C0
S3 = solve(real(Z31)==R0, imag(Z31)==-1/(w31*C0));
R03 = double(S3.R0);
C03 = double(S3.C0);
R03 = real(R03);
C03 = real(C03);

if isempty(R03)==1
    R03=0;
end
if isempty(C03)==1
    C03=0;
end
% %------------------------------------------------------------------------------

% %------------------------------------------------------------------------------
% % Ersatzschaltbild 4:
% % Anzahl unbekannter Elemente: 2
syms R0 C0
S4 = solve (real(Z31)==(1/R0)/((w31*C0)^2+(1/(R0^2))), imag(Z31)==(w31*C0)/((w31*C0)^2+(1/(R0^2))));
R04 = double(S4.R0);
C04 = double(S4.C0);
R04 = real(R04);
C04 = real(C04);
if isempty(R04)==1
    R04=0;
end
if isempty(C04)==1
    C04=0;
end
% %------------------------------------------------------------------------------

% %------------------------------------------------------------------------------
% % Ersatzschaltbild 5:
% % Anzahl unbekannter Elemente: 3
syms R0 L0 C0
S5 = solve(real(Z31)==R0, imag(Z31)==w31*L0-1/(w31*C0), imag(Z32)==w32*L0-1/(w32*C0));
R05 = double(S5.R0);
L05 = double(S5.L0);
C05 = double(S5.C0);
R05 = real(R05);
L05 = real(L05);
C05 = real(C05);
if isempty(R05)==1
    R05=0;
end
if isempty(L05)==1
    L05=0;
end
if isempty(C05)==1
    C05=0;
end
% Z = R05+j*w31*L05+1/(j*w31*C05)
% 
% %------------------------------------------------------------------------------

% %------------------------------------------------------------------------------
% % Ersatzschaltbild 6:
% % Anzahl unbekannter Elemente: 3
syms R0 L0 C0
S6 = solve(real(1/Z31)==1/R0, imag(1/Z31)==w31*C0-1/(w31*L0), imag(1/Z32)==w32*C0-1/(w32*L0));
R06 = double(S6.R0);
L06 = double(S6.L0);
C06 = double(S6.C0);
R06 = real(R06);
L06 = real(L06);
C06 = real(C06);
if isempty(R06)==1
    R06=0;
end
if isempty(L06)==1
    L06=0;
end
if isempty(C06)==1
    C06=0;
end
% %------------------------------------------------------------------------------

% %------------------------------------------------------------------------------
% % Ersatzschaltbild 7:
% % Anzahl unbekannter Elemente: 3
syms R0 L0 C0
S7 = solve (1/real(1/Z31)==R0+(w31*L0)^2/R0, 1/real(1/Z32)==R0+(w32*L0)^2/R0);
R07 = double(S7.R0);
L07 = double(S7.L0);

if R07(1)==R07(end)
    R07f = R07(1);
end
if R07(1)~=R07(end)
   R07 = max(R07); 
   R07f = R07(1);
end

if L07(1)==L07(end)
    L07f = L07(1);
end
if L07(1)~=L07(end)
   L07 = max(L07);
   L07f = L07(1);
end

S7 = solve (imag(1/Z31)==w31*C0-w31*L07f/(R07f^2+(w31*L07f)^2));
C07f = double(S7);

R07 = real(R07f);
L07 = real(L07f);
C07 = real(C07f);

if isempty(R07)==1
    R07=0;
end
if isempty(L07)==1
    L07=0;
end
if isempty(C07)==1
    C07=0;
end
% 
% %------------------------------------------------------------------------------

% %------------------------------------------------------------------------------
% % Ersatzschaltbild 8:
% % Anzahl unbekannter Elemente: 3
syms R0 L0 C0
S8 = solve (real(Z31)==(1/R0)/(1/R0^2+(w31*C0)^2), real(Z32)==(1/R0)/(1/R0^2+(w32*C0)^2));
R08 = double(S8.R0);
C08 = double(S8.C0);

if R08(1)==R08(end)
    R08f = R08(1);
end
if R08(1)~=R08(end)
   R08 = max(R08); 
   R08f = R08(1);
end

if C08(1)==C08(end)
    C08f = C08(1);
end
if C08(1)~=C08(end)
   C08 = max(C08);
   C08f = C08(1);
end

S8 = solve (imag(Z31)==w31*L0-w31*C08f/(1/R08f^2+(w31*C08f)^2));
L08f = double(S8);

R08 = real(R08f);
L08 = real(L08f);
C08 = real(C08f);

if isempty(R08)==1
    R08=0;
end
if isempty(L08)==1
    L08=0;
end
if isempty(C08)==1
    C08=0;
end
% 
% %------------------------------------------------------------------------------

% %------------------------------------------------------------------------------
% % Ersatzschaltbild 9:
% % Anzahl unbekannter Elemente: 3
syms R091 R092 C0
S09 = solve (imag(Z31)==-w31*C0/(1/R091^2+(w31*C0)^2), imag(Z32)==-w32*C0/(1/R091^2+(w32*C0)^2),real(Z31)==R092+1/R091/(1/R091^2+(w31*C0)^2));

R091 = double(S09.R091);
R092 = double(S09.R092);
C09 = double(S09.C0);

if R091(1)==R091(end)
    R091f = R091(1);
end
if R091(1)~=R091(end)
   R091 = max(R091); 
   R091f = R091(1);
end

if R092(1)==R092(end)
    R092f = R092(1);
end
if R092(1)~=R092(end)
   R092 = max(R092); 
   R092f = R092(1);
end

if C09(1)==C09(end)
    C09f = C09(1);
end
if C09(1)~=C09(end)
   C09 = max(C09);
   C09f = C09(1);
end

R091 = real(R091f);
R092 = real(R092f);
C09 = real(C09f);

if isempty(R091)==1
    R091=0;
end
if isempty(R092)==1
    R092=0;
end
if isempty(C09)==1
    C09=0;
end
% 
% Z = 1/(1i*w31*C09+1/R091)+R092
% 
% %------------------------------------------------------------------------------

% %------------------------------------------------------------------------------
% % Ersatzschaltbild 10:
% % Anzahl unbekannter Elemente: 4
% syms R101 R102 L0 C0
% S10 = solve (imag(Z31)==w31*L0-w31*C0/(1/R101^2+(w31*C0)^2), imag(Z32)==w32*L0-w32*C0/(1/R101^2+(w32*C0)^2), imag(Z33)==w33*L0-w33*C0/(1/R101^2+(w33*C0)^2), real(Z31)==R102+1/R101/(1/R101^2+(w31*C0)^2));
% 
% R101 = double(S10.R101);
% R102 = double(S10.R102);
% L10 = double(S10.L0);
% C10 = double(S10.C0);
% 
% if R101(1)==R101(end)
%     R101f = R101(1);
% end
% if R101(1)~=R101(end)
%    R101 = max(R101); 
%    R101f = R101(1);
% end
% 
% if R102(1)==R102(end)
%     R102f = R102(1);
% end
% if R102(1)~=R102(end)
%    R102 = max(R102); 
%    R102f = R102(1);
% end
% 
% if L10(1)==L10(end)
%     L10f = L10(1);
% end
% if L10(1)~=L10(end)
%    L10 = max(L10);
%    L10f = L10(1);
% end
% 
% if C10(1)==C10(end)
%     C10f = C10(1);
% end
% if C10(1)~=C10(end)
%    C10 = max(C10);
%    C10f = C10(1);
% end
% 
% R101 = real(R101f);
% R102 = real(R102f);
% L10 = real(L10f);
% C10 = real(C10f);
% 
% Z = 1/(1i*w31*C10+1/R101)+R102+1i*w31*L10
% 
% %------------------------------------------------------------------------------

%------------------------------------------------------------------------------
% Ersatzschaltbild 12:
% Anzahl unbekannter Elemente: 4
% syms R121 R122 L0 C0 positiv
% S12 = solve (imag(1/Z31)==w31*C0-w31*L0/(R122^2+(w31*L0)^2), imag(1/Z32)==w32*C0-w32*L0/(R122^2+(w32*L0)^2), imag(1/Z33)==w33*C0-w33*L0/(R122^2+(w33*L0)^2), real(Z31)==1/R121+R122/(R122^2+(w31*L0)^2));
% 
% R121 = double(S12.R121);
% R122 = double(S12.R122);
% L12 = double(S12.L0);
% C12 = double(S12.C0);
% 
% % R121 = single(R121);
% % R122 = single(R122);
% % C12 = single(C12);
% % L12 = single(L12);
% 
% if R121(1)==R121(2)
%     R121f = R121(1);
% end
% if R121(1)~=R121(2)
%    R121 = max(R121); 
%    R121f = R121(1);
% end
% 
% if R122(1)==R122(2)
%     R122f = R122(1);
% end
% if R122(1)~=R122(2)
%    R122 = max(R122); 
%    R122f = R122(1);
% end
% 
% if L12(1)==L12(2)
%     L12f = L12(1);
% end
% if L12(1)~=L12(2)
%    L12 = max(L12);
%    L12f = L12(1);
% end
% 
% if C12(1)==C12(2)
%     C12f = C12(1);
% end
% if C12(1)~=C12(2)
%    C12 = max(C12);
%    C12f = C12(1);
% end
% 
% R121 = real(R121f)
% R122 = real(R122f)
% C12 = real(C12f)
% L12 = real(L12f)

%Z31 = 1/(1/R121+1/(j*w31*L12+R122)+j*w31*C12)
%------------------------------------------------------------------------------

A = [R01,1,1,1,L01,1,1
     R02,1,1,1,L02,1,1
     R03,1,1,1,1,C03,1
     R04,1,1,1,1,C04,1
     R05,1,1,1,L05,C05,1
     R06,1,1,1,L06,C06,1
     R07,1,1,1,L07,C07,1
     R08,1,1,1,L08,C08,1
     R091,1,1,R092,1,C09,1
     1,1,1,1,1,1,1
     1,1,1,1,1,1,1
     1,1,1,1,1,1,1
     1,1,1,1,1,1,1
     1,1,1,1,1,1,1
     1,1,1,1,1,1,1
     1,1,1,1,1,1,1
     1,1,1,1,1,1,1
     1,1,1,1,1,1,1
     1,1,1,1,1,1,1
     1,1,1,1,1,1,1
     1,1,1,1,1,1,1];
 %A=eval(A);
     
end
%------------------------------------------------------------------------------
% ENDE
%------------------------------------------------------------------------------