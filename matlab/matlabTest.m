% Reads a sample file and plots all possible data

clf
addpath 'incomming/s-param_toolbox/sbox'

IN.file = ['../sample_files/bsp6.s1p'];
 
% read Datafile
fid_log = fopen('SXPParse_log.txt','w');
% freq: Frequency Points
% data: Data in Scattering format
% freq_noise, data_noise not used
% Zo: reference resistance
[freq, data, freq_noise, data_noise, Zo] = SXPParse(IN.file, fid_log);


%data = s2z(data);
%data = s2y(Zo,data);
%data2=data(:); % Data to compares

scattering=data(:);

impedance=s2z(Zo, data);
impedance=impedance(:);

admittance=s2y(Zo, data);
admittance=admittance(:);

% % Plott scattering
% subplot(221)
% semilogx(freq, real(scattering), 'LineWidth', 2)
% hold on
% semilogx(freq, imag(scattering), 'LineWidth', 2)
% grid on
% legend('real','imag')
% title('S')
% 
% % Plott impedance
% subplot(222)
% loglog(freq, real(imdecande), 'LineWidth', 2)
% hold on
% loglog(freq, imag(imdecande), 'LineWidth', 2)
% grid on
% legend('real','imag')
% title('Z')
% 
% % Plott admittance
% subplot(223)
% semilogx(freq, real(admittance), 'LineWidth', 2)
% hold on
% semilogx(freq, imag(admittance), 'LineWidth', 2)
% grid on
% legend('real','imag')
% title('Y')