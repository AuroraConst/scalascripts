
# Set the file association for .sc files
Set-ItemProperty -Path "HKCU:\Software\Classes\.sc" -Name "(Default)" -Value "ScalaScript"

# Set the command for the ScalaScript file type
New-Item -Path "HKCU:\Software\Classes\ScalaScript\shell\open\command" -Force
Set-ItemProperty -Path "HKCU:\Software\Classes\ScalaScript\shell\open\command" -Name "(Default)" -Value '"C:\dev\cs\.scala-cli.aux.exe" run "%1" %*'

Write-Output "File association for .sc files has been configured."