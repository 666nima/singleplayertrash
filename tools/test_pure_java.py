"""Run standalone placement/eligibility tests without Minecraft or Gradle."""
from pathlib import Path
import os
import subprocess
import tempfile

root = Path(__file__).resolve().parents[1]
java_home = os.environ.get('JAVA_HOME')
def executable(name):
    return str(Path(java_home) / 'bin' / (name + ('.exe' if os.name == 'nt' else ''))) if java_home else name

with tempfile.TemporaryDirectory(prefix='singleplayertrash-tests-') as output:
    sources = [root / 'src/main/java/dev/singleplayertrash' / name for name in ('ButtonPlacement.java', 'Eligibility.java')]
    sources += sorted((root / 'tests').glob('*.java'))
    subprocess.run([executable('javac'), '-encoding', 'UTF-8', '-d', output, *map(str, sources)], check=True)
    for name in ('PlacementTest', 'PolicyTest'):
        subprocess.run([executable('java'), '-cp', output, 'dev.singleplayertrash.' + name], check=True)
