%%Main
clear
clc
tic
[w,yz,ys]=filereader();

%options
fMax = 1e10;
fMin = 1e0;
anzElementeMin=0;
anzElementeMax=2;
skin=1;
[w,yz,ys,ES]=inputModifier(anzElementeMin,anzElementeMax,fMin,fMax,skin,w,yz,ys);
%A=analyticSolver[w,z];
%Der analytic solver versucht durch analytische Methoden geeignete 
%Startwerte für fminsearch zu finden. Die Werte müssen erfahrungsgemäss
%auf eine dekade genau stimmen

A=ones(21,7); %Parametermatrix (ersatz für analyticSolver
A(5,1)=200;       %Test R  
A(5,5)=100e-6;      %Test L
A(5,6)=1e-7;    %Test C0


%syms r0 f0 alpha r1 l c0 c1
%Zählerpolynome siehe Excel
%Jede Z-Formel der ES kann auf einen Nenner gebracht werden.
%Der Nenner und der Zähler werden dann zu Polynomen und der gesamte
%Ausdruck zu einer Polynomdivision.

%Ersatz in s.m
% %Zählerpolynome:
% Pz=[0,l,r0                      
%     0,l*r0,0
%     0,c0*r0,1
%     0,0,r0
%     l*c0,r0*c0,1
%     0,r0*l,0
%     0,l,r0
%     0,0,r0
%     0,c0*r0*r1,r0+r1
%     l*c0*r0,l+c0*r0*r1,r0
%     c0*l*r0*r1,l*r0+l*r1,r1*r0
%     0,l,r1
%     c1*c0*l,c0*c1*r0,c1+c0];
% %Nennerpolynome:
% Pn= [0,0,1
%     0,l,r0
%     0,c0,0
%     0,c0*r0,1
%     0,c0,0
%     0,l,r0
%     c0*l,c0*r0,1
%     0,c0*r0,1
%     0,c0*r0,1
%     0,c0*r0,1
%     c0*l*r0,l,r0
%     c0*l,c0*r1+l*r0,r1*r0+1
%     0,0,c1];

rangliste=analyzer(w,ys,A,ES)
%Die Rangliste der ES wird mit den analytisch bestimmten Parameter erstellt
%Sie gibt an, welches ES die kleinste Fehlersumme aufweist

 

options = optimset('TolFun',1e-2,'MaxFunEvals',100000,'MaxIter',100000);
Parameter1Platz=fminsearch(@(a) fehlersummeSAbs(rangliste(1),w,ys,a),A(rangliste(1),:),options)
%s(n,w,A,Pz,Pn)



% Plotting
%f1=figure;
figure('units','normalized','outerposition',[0 0 1 1])
%s-Parameter Mag
p1=subplot(211); grid on; hold on;
set(p1,'XScale','log');

plot(w,abs(ys),'.-')
plot(w,abs(s(rangliste(1),w,A(rangliste(1),:))),'r')
plot(w,abs(s(rangliste(1),w,Parameter1Platz)),'g')

title('S-Parameter Magnitude')
legend('Eingelesene Daten','Bestes Analytisches Resultat','Optimiert');
xlabel('\omega');


%Fehler in dB
p2=subplot(212); hold on; grid on;
set(p2,'XScale','log');
ylabel('dB');

h1=transpose(s(rangliste(1),w,Parameter1Platz));
fehlers1=ys-h1;
fehler1db=20*log10(abs(fehlers1));

h2=transpose(s(rangliste(1),w,A(rangliste(1),:)));
fehlers2=ys-h2;
fehler2db=20*log10(abs(fehlers2));
plot(w,fehler1db,w,fehler2db);

title('Deviation Magnitude')
legend('Abweichung nach Optimierung','Abweichung Analytisches Resultat');
xlabel('\omega');


fehlersumme=fehlersummeSAbs(rangliste(1),w,ys,Parameter1Platz)
toc





