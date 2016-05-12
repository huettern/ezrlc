%%Main
clear all hidden
clc
tic
[w,yz,ys,Rbez]=filereader();

%options

%ES-Liste für Tooplogien mit 2 Elementen
fMax = 1e10/pi/2;
fMin = 1e4/pi/2;
anzElementeMin=2;
anzElementeMax=2;
skin=0;
[w,yz,ys,ES2]=inputModifier(anzElementeMin,anzElementeMax,fMin,fMax,skin,w,yz,ys);
%ES-Liste für Tooplogien mit 3 Elementen
anzElementeMin=3; 
anzElementeMax=3;
[w,yz,ys,ES3]=inputModifier(anzElementeMin,anzElementeMax,fMin,fMax,skin,w,yz,ys);


A=AnalyticSolver(w,yz,ys);
% Der analytic solver versucht durch analytische Methoden geeignete 
% Startwerte für fminsearch zu finden. Die Werte müssen erfahrungsgemäss
% auf eine dekade genau stimmen
%A ist die Parameter-Matrix 

%  A=ones(21,7); %Parametermatrix (ersatz für analyticSolver
%  A(1,1)=0.01;       %Test R  
%  A(1,5)=836e-6;      %Test L
% A(5,6)=1e-7;    %Test C0


rangliste2=analyzer(w,yz,A,ES2) %Rangliste der 2 ES mit 2 Elementen
rangliste3=analyzer(w,yz,A,ES3) %Rangliste der 2 ES mit 3 Elementen
%Die Rangliste der ES wird mit den analytisch bestimmten Parametern erstellt
%Sie gibt an, welches ES die kleinste Fehlersumme aufweist


 
%Optionen für fminsearch
options = optimset('TolFun',1e-12,'TolX',1e-12,'MaxFunEvals',10000,'MaxIter',10000);

%Parameter der ES mit 2 Elementen Optimieren
Parameter1Platz2=fminsearch(@(a) fehlersummeSAbs(rangliste2(1),w,ys,a,Rbez),A(rangliste2(1),:),options);
Parameter2Platz2=fminsearch(@(a) fehlersummeSAbs(rangliste2(2),w,ys,a,Rbez),A(rangliste2(2),:),options);

%Parameter der ES mit 3 Elementen Optimieren
Parameter1Platz3=fminsearch(@(a) fehlersummeSAbs(rangliste3(1),w,ys,a,Rbez),A(rangliste3(1),:),options);
Parameter2Platz3=fminsearch(@(a) fehlersummeSAbs(rangliste3(2),w,ys,a,Rbez),A(rangliste3(2),:),options);

%Beste 2 ES mit 2 Elementen auf 3 Elemente erweitern
%Der Zusätzliche Parameter wird analytisch bei einem Punkt bestimmt
[ES1Platz3,Parameter1Platz3ext]=ESextend(rangliste2(1),Parameter1Platz2,w,ys,yz);
[ES2Platz3,Parameter2Platz3ext]=ESextend(rangliste2(2),Parameter2Platz2,w,ys,yz);

%Erweiterte Schaltbilder mit 3 ES optimieren
%Parameter1Platz3ext=fminsearch(@(a) fehlersummeZAbs(ES1Platz3,w,yz,a),AnalyticParameter1Platz3ext,options);
%Parameter2Platz3ext=fminsearch(@(a) fehlersummeZAbs(ES2Platz3,w,yz,a),AnalyticParameter2Platz3ext,options);


%Fehlersummen Bestimmen und in Matrix schreiben
Zfehlersumme1Platz2=fehlersummeZAbs(rangliste2(1),w,yz,Parameter1Platz2);
Zfehlersumme2Platz2=fehlersummeZAbs(rangliste2(2),w,yz,Parameter2Platz2);
%1.Platz mit 2 Elementen
fehlersummen=[rangliste2(1),Zfehlersumme1Platz2,Parameter1Platz2
              rangliste2(2),Zfehlersumme2Platz2,Parameter2Platz2];
rangliste=sortrows(fehlersummen,2);
Platz12=rangliste(1,1);
fehlersummePlatz12=rangliste(1,2);
parPlatz12=rangliste(1,3:9);

%Fehlersummen Bestimmen und in Matrix schreiben
Zfehlersumme1Platz3=fehlersummeZAbs(rangliste3(1),w,yz,Parameter1Platz3);
Zfehlersumme2Platz3=fehlersummeZAbs(rangliste3(2),w,yz,Parameter2Platz3);
%1.Platz mit 3 Elementen
fehlersummen=[rangliste3(1),Zfehlersumme1Platz3,Parameter1Platz3
              rangliste3(2),Zfehlersumme2Platz3,Parameter2Platz3];
rangliste=sortrows(fehlersummen,2);
Platz13=rangliste(1,1);
fehlersummePlatz13=rangliste(1,2);
parPlatz13=rangliste(1,3:9);

%Fehlersummen Bestimmen und in Matrix schreiben
Zfehlersumme1Platz3ext=fehlersummeZAbs(ES1Platz3,w,yz,Parameter1Platz3ext);
Zfehlersumme2Platz3ext=fehlersummeZAbs(ES2Platz3,w,yz,Parameter2Platz3ext);
%1.Platz mit 3 Elementen erweitert
fehlersummen=[ES1Platz3,Zfehlersumme1Platz3ext,Parameter1Platz3ext
              ES2Platz3,Zfehlersumme2Platz3ext,Parameter2Platz3ext];
rangliste=sortrows(fehlersummen,2);
Platz13ext=rangliste(1,1);
fehlersummePlatz13ext=rangliste(1,2);
parPlatz13ext=rangliste(1,3:9);


%Alle Lösungen zusammenführen und eine Rangliste erstellen
ZfehlersummePlatz12=fehlersummeZAbs(Platz12,w,yz,parPlatz12);
ZfehlersummePlatz13=fehlersummeZAbs(Platz13,w,yz,parPlatz13);
ZfehlersummePlatz13ext=fehlersummeZAbs(Platz13ext,w,yz,parPlatz13ext);

fehlersummen=[Platz12,ZfehlersummePlatz12,parPlatz12
              Platz13,ZfehlersummePlatz13,parPlatz13
              Platz13ext,ZfehlersummePlatz13ext,parPlatz13ext];
rangliste=sortrows(fehlersummen,2);
Platz1=rangliste(1,1)
ZfehlersummePlatz1=rangliste(1,2);
Platz1par=rangliste(1,3:9)
           

% Plotting
figure('units','normalized','outerposition',[0 0 1 1])
%s-Parameter Mag
p1=subplot(221); grid on; hold on;
set(p1,'XScale','log');

plot(w,abs(ys),'b.-')
plot(w,abs(s(Platz1,w,Platz1par,Rbez)),'g.')

title('S-Parameter Magnitude')
legend('Eingelesene Daten','Optimiert');
xlabel('\omega');


%Fehler in dB
p2=subplot(223); hold on; grid on;
set(p2,'XScale','log');
ylabel('dB');

h1=transpose(s(Platz1,w,Platz1par,Rbez));
fehlers1=abs(ys)-abs(h1);
fehler1db=20*log10(abs(fehlers1));

plot(w,fehler1db);

title('Deviation Magnitude')
legend('Abweichung nach Optimierung');
xlabel('\omega');



p3=subplot(222); hold on; grid on;
set(p3,'XScale','log');
set(p3,'YScale','log');
ylabel('|Z|');

plot(w,abs(yz),'b.-');
plot(w,abs(z(Platz1,w,Platz1par)),'g');

legend('Messwerte','Optimiert');



p4=subplot(224); hold on; grid on;
set(p4,'XScale','log');
ylabel('angle(Z)');

plot(w,angle(yz),'b.-');
plot(w,angle(z(Platz1,w,Platz1par)),'g');

legend('Messwerte','Optimiert');


















% Plotting
%f1=figure;
% figure('units','normalized','outerposition',[0 0 1 1])
% %s-Parameter Mag
% p1=subplot(231); grid on; hold on;
% set(p1,'XScale','log');
% 
% plot(w,abs(ys),'.-')
% plot(w,abs(s(rangliste(1),w,A(rangliste(1),:),Rbez)),'r')
% plot(w,abs(s(rangliste(1),w,Parameter1Platz2,Rbez)),'g')
% 
% title('S-Parameter Magnitude')
% legend('Eingelesene Daten','Bestes Analytisches Resultat','Optimiert');
% xlabel('\omega');
% 
% 
% %Fehler in dB
% p2=subplot(234); hold on; grid on;
% set(p2,'XScale','log');
% ylabel('dB');
% 
% h1=transpose(s(rangliste(1),w,Parameter1Platz2,Rbez));
% fehlers1=abs(ys)-abs(h1);
% fehler1db=20*log10(abs(fehlers1));
% 
% h2=transpose(s(rangliste(1),w,A(rangliste(1),:),Rbez));
% fehlers2=abs(ys)-abs(h2);
% fehler2db=20*log10(abs(fehlers2));
% 
% plot(w,fehler1db,w,fehler2db);
% 
% title('Deviation Magnitude')
% legend('Abweichung nach Optimierung','Abweichung Analytisches Resultat');
% xlabel('\omega');
% 
% 
% % p5=subplot(232); grid on; hold on;
% % set(p5,'XScale','log');
% % 
% % plot(w,abs(ys),'.-')
% % plot(w,abs(s(rangliste(2),w,A(rangliste(2),:),Rbez)),'r')
% % plot(w,abs(s(rangliste(2),w,Parameter2Platz,Rbez)),'g')
% % 
% % title('S-Parameter Magnitude')
% % legend('Eingelesene Daten','Bestes Analytisches Resultat','Optimiert');
% % xlabel('\omega');
% 
% 
% %Fehler in dB
% % p6=subplot(235); hold on; grid on;
% % set(p6,'XScale','log');
% % ylabel('dB');
% % 
% % h1=transpose(s(rangliste(2),w,Parameter2Platz,Rbez));
% % fehlers1=abs(ys)-abs(h1);
% % fehler1db=20*log10(abs(fehlers1));
% % 
% % h2=transpose(s(rangliste(2),w,A(rangliste(2),:),Rbez));
% % fehlers2=ys-h2;
% % fehler2db=20*log10(abs(fehlers2));
% % plot(w,fehler1db,w,fehler2db);
% % 
% % title('Deviation Magnitude')
% % legend('Abweichung nach Optimierung','Abweichung Analytisches Resultat');
% % xlabel('\omega');
% 
% % p3=subplot(233); hold on; grid on;
% % set(p3,'XScale','log');
% % set(p3,'YScale','log');
% % ylabel('|Z|');
% % 
% % plot(w,abs(z(rangliste(1),w,A(rangliste(1),:))),'r');
% % plot(w,abs(z(rangliste(1),w,Parameter3Platz)),'g');
% % plot(w,abs(yz),'b');
% % legend('AnalyticSolver','Optimiert','Messerte');
% % 
% % p4=subplot(236); hold on; grid on;
% % set(p4,'XScale','log');
% % ylabel('angle(Z)');
% % 
% % plot(w,angle(z(rangliste(1),w,A(rangliste(1),:))),'r');
% % plot(w,angle(z(rangliste(1),w,Parameter1Platz)),'g');
% % plot(w,angle(yz),'b');
% % legend('AnalyticSolver','Optimiert','Messerte');
% 
% % p5=subplot(233); grid on; hold on;
% % set(p5,'XScale','log');
% % 
% % plot(w,abs(ys),'.-')
% % plot(w,abs(s(rangliste(3),w,A(rangliste(3),:),Rbez)),'r')
% % plot(w,abs(s(rangliste(3),w,Parameter3Platz,Rbez)),'g')
% % 
% % title('S-Parameter Magnitude')
% % legend('Eingelesene Daten','Bestes Analytisches Resultat','Optimiert');
% % xlabel('\omega');
% % 
% % p6=subplot(236); hold on; grid on;
% % set(p6,'XScale','log');
% % ylabel('dB');
% % 
% % h1=transpose(s(rangliste(3),w,Parameter3Platz,Rbez));
% % fehlers1=abs(ys)-abs(h1);
% % fehler1db=20*log10(abs(fehlers1));
% % 
% % h2=transpose(s(rangliste(3),w,A(rangliste(3),:),Rbez));
% % fehlers2=abs(ys)-abs(h2);
% % fehler2db=20*log10(abs(fehlers2));
% % 
% % plot(w,fehler1db,w,fehler2db);
% % 
% % title('Deviation Magnitude')
% % legend('Abweichung nach Optimierung','Abweichung Analytisches Resultat');
% % xlabel('\omega');
% 
% 
% % 
% % p3=subplot(233); hold on; grid on;
% % set(p3,'XScale','log');
% % ylabel('|Z|');
% % 
% % plot(w,abs(z(rangliste(3),w,A(rangliste(3),:))),'r');
% % plot(w,abs(z(rangliste(3),w,Parameter3Platz)),'g');
% % plot(w,abs(yz),'b');
% % legend('AnalyticSolver','Optimiert','Messerte');
% 
% p10=subplot(232); grid on; hold on;
% set(p10,'XScale','log');
% 
% plot(w,abs(ys),'.-')
% plot(w,abs(s(ES1Platz3,w,AnalyticParameter1Platz3ext,Rbez)),'r')
% plot(w,abs(s(ES1Platz3,w,Parameter1Platz3,Rbez)),'g')
% 
% title('S-Parameter Magnitude')
% legend('Eingelesene Daten','Bestes Analytisches Resultat','Optimiert');
% xlabel('\omega');
% 
% 
% %Fehler in dB
% p11=subplot(235); hold on; grid on;
% set(p11,'XScale','log');
% ylabel('dB');
% 
% h1=transpose(s(ES1Platz3,w,Parameter1Platz3,Rbez));
% fehlers1=abs(ys)-abs(h1);
% fehler1db=20*log10(abs(fehlers1));
% 
% h2=transpose(s(ES1Platz3,w,AnalyticParameter1Platz3ext,Rbez));
% fehlers2=abs(ys)-abs(h2);
% fehler2db=20*log10(abs(fehlers2));
% 
% plot(w,fehler1db,w,fehler2db);
% 
% title('Deviation Magnitude')
% legend('Abweichung nach Optimierung','Abweichung Analytisches Resultat');
% xlabel('\omega');
% 
% 
% 
% p12=subplot(233); hold on; grid on;
% set(p12,'XScale','log');
% set(p12,'YScale','log');
% ylabel('|Z|');
% 
% plot(w,abs(yz),'b.-');
% plot(w,abs(z(rangliste(1),w,Platz1Par)),'g');
% legend('Messerte','Optimiert');
% 
% p13=subplot(236); hold on; grid on;
% set(p13,'XScale','log');
% ylabel('angle(Z)');
% 
% 
% plot(w,angle(yz),'b.-');
% plot(w,angle(z(Platz1,w,Platz1Par)),'g');
% legend('Messerte','Optimiert');


%fehlersumme=fehlersummeSAbs(rangliste(1),w,ys,Parameter1Platz,Rbez)
toc





